package com.purgeteam.dynamic.config.starter.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.web.context.support.StandardServletEnvironment;

/**
 * @author purgeyao
 * @since 1.0
 */
public class PropertyUtil {

    private static final String BEFORE = "before";

    private static final String AFTER = "after";

    private Set<String> standardSources = new HashSet<>(
            Arrays.asList(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME,
                    StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME,
                    StandardServletEnvironment.JNDI_PROPERTY_SOURCE_NAME,
                    StandardServletEnvironment.SERVLET_CONFIG_PROPERTY_SOURCE_NAME,
                    StandardServletEnvironment.SERVLET_CONTEXT_PROPERTY_SOURCE_NAME,
                    "configurationProperties"));

    public Map<String, HashMap> contrast(MutablePropertySources beforeSources,
                                         MutablePropertySources afterSources) {
        Map<String, Object> before = extract(beforeSources);
        Map<String, HashMap> propertyMap = changesAll(before, extract(afterSources));
        return propertyMap;
    }

    private Map<String, Object> extract(MutablePropertySources propertySources) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<PropertySource<?>> sources = new ArrayList<PropertySource<?>>();
        for (PropertySource<?> source : propertySources) {
            sources.add(0, source);
        }
        for (PropertySource<?> source : sources) {
            if (!this.standardSources.contains(source.getName())) {
                extract(source, result);
            }
        }
        return result;
    }

    private void extract(PropertySource<?> parent, Map<String, Object> result) {
        if (parent instanceof CompositePropertySource) {
            try {
                List<PropertySource<?>> sources = new ArrayList<PropertySource<?>>();
                for (PropertySource<?> source : ((CompositePropertySource) parent)
                        .getPropertySources()) {
                    sources.add(0, source);
                }
                for (PropertySource<?> source : sources) {
                    extract(source, result);
                }
            } catch (Exception e) {
                return;
            }
        } else if (parent instanceof EnumerablePropertySource) {
            for (String key : ((EnumerablePropertySource<?>) parent).getPropertyNames()) {
                result.put(key, parent.getProperty(key));
            }
        }
    }

    private Map<String, HashMap> changesAll(Map<String, Object> before,
                                            Map<String, Object> after) {

        HashMap<String, HashMap> result = new HashMap<>(16);
        for (String key : before.keySet()) {
            HashMap<String, String> valueMap = new HashMap<>(16);
            valueMap.put(BEFORE, String.valueOf(before.get(key)));
            if (!after.containsKey(key)) {
                valueMap.put(AFTER, null);
                result.put(key, valueMap);
            } else if (!equal(before.get(key), after.get(key))) {
                valueMap.put(AFTER, String.valueOf(after.get(key)));
                result.put(key, valueMap);
            }
        }
        for (String key : after.keySet()) {
            if (!before.containsKey(key)) {
                HashMap<String, String> valueMap = new HashMap<>(16);
                valueMap.put(BEFORE, null);
                valueMap.put(AFTER, String.valueOf(after.get(key)));
                result.put(key, valueMap);
            }
        }
        return result;
    }

    private Map<String, Object> changes(Map<String, Object> before,
                                        Map<String, Object> after) {
        Map<String, Object> result = new HashMap<String, Object>();
        for (String key : before.keySet()) {
            if (!after.containsKey(key)) {
                result.put(key, null);
            } else if (!equal(before.get(key), after.get(key))) {
                result.put(key, after.get(key));
            }
        }
        for (String key : after.keySet()) {
            if (!before.containsKey(key)) {
                result.put(key, after.get(key));
            }
        }
        return result;
    }

    private boolean equal(Object one, Object two) {
        if (one == null && two == null) {
            return true;
        }
        if (one == null || two == null) {
            return false;
        }
        return one.equals(two);
    }

}
