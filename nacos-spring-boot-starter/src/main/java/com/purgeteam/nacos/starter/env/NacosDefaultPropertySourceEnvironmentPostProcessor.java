package com.purgeteam.nacos.starter.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

import static org.springframework.core.io.support.ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX;

/**
 * A lowest precedence {@link EnvironmentPostProcessor} implementation to append Core default {@link PropertySource}
 * with lowest order in {@link Environment}
 *
 * @author <a href="mailto:yaoonlyi@gmail.com">purgeyao</a>
 * @since 1.0.0
 */
public class NacosDefaultPropertySourceEnvironmentPostProcessor implements EnvironmentPostProcessor,
        Ordered {

    private static final String PROPERTY_SOURCE_NAME = "nacos";

    private static final String RESOURCE_LOCATION_PATTERN = CLASSPATH_ALL_URL_PREFIX + "META-INF/nacos/nacos-${spring.profiles.active}.properties";

    private static final String FILE_ENCODING = "UTF-8";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        ResourceLoader resourceLoader = getResourceLoader(application);

        processPropertySource(environment, resourceLoader);
    }

    private ResourceLoader getResourceLoader(SpringApplication application) {

        ResourceLoader resourceLoader = application.getResourceLoader();

        if (resourceLoader == null) {
            resourceLoader = new DefaultResourceLoader(application.getClassLoader());
        }

        return resourceLoader;
    }

    private void processPropertySource(ConfigurableEnvironment environment, ResourceLoader resourceLoader) {
        try {
            PropertySource coreDefaultPropertySource = buildPropertySource(environment, resourceLoader);
            MutablePropertySources propertySources = environment.getPropertySources();
            // append coreDefaultPropertySource as last one in order to be overrided by higher order
            propertySources.addLast(coreDefaultPropertySource);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private PropertySource buildPropertySource(ConfigurableEnvironment environment, ResourceLoader resourceLoader) throws IOException {
        CompositePropertySource propertySource = new CompositePropertySource(PROPERTY_SOURCE_NAME);
        appendPropertySource(environment, propertySource, resourceLoader);
        return propertySource;
    }

    private void appendPropertySource(ConfigurableEnvironment environment, CompositePropertySource propertySource, ResourceLoader resourceLoader) throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver(
                resourceLoader);
        // 占位符解析
        String resourceLocationPattern = environment.resolvePlaceholders(RESOURCE_LOCATION_PATTERN);
        Resource[] resources = resourcePatternResolver.getResources(resourceLocationPattern);
        for (Resource resource : resources) {
            // Add if exists
            if (resource.exists()) {
                String internalName = String.valueOf(resource.getURL());
                propertySource.addPropertySource(new ResourcePropertySource(internalName, new EncodedResource(resource, FILE_ENCODING)));
            }
        }
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

}
