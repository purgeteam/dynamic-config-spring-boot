package com.purgeteam.dynamic.config.starter.event;

import com.purgeteam.dynamic.config.starter.util.PropertyUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

/**
 * @author purgeyao
 * @since 1.0
 */
public class DynamicConfigApplicationListener implements ApplicationListener<RefreshEvent>,
        ApplicationContextAware, Ordered {

    private static final Logger log = LoggerFactory.getLogger(DynamicConfigApplicationListener.class);

    private ContextRefresher refresh;

    private ApplicationContext context;

    private PropertyUtil propertyUtil;

    public DynamicConfigApplicationListener(ContextRefresher contextRefresher) {
        this.refresh = contextRefresher;
    }

    public DynamicConfigApplicationListener(ContextRefresher contextRefresher, PropertyUtil propertyUtil) {
        this.refresh = contextRefresher;
        this.propertyUtil = propertyUtil;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    @Override
    public void onApplicationEvent(RefreshEvent event) {
        ConfigurableEnvironment beforeEnv = (ConfigurableEnvironment) context.getEnvironment();
        MutablePropertySources propertySources = beforeEnv.getPropertySources();
        MutablePropertySources beforeSources = new MutablePropertySources(propertySources);
        // 刷新上下文
        Set<String> refresh = this.refresh.refresh();
        // 获取对比值发布事件
        Map<String, HashMap> contrast = propertyUtil.contrast(beforeSources, propertySources);
        context.publishEvent(new ActionConfigEvent(this, "Refresh config", contrast));
        log.info("[ActionApplicationListener] The update is successful {}", refresh);
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE - 1;
    }

}
