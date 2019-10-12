package com.purgeteam.dynamic.config.starter;

import com.purgeteam.dynamic.config.starter.event.DynamicConfigApplicationListener;
import com.purgeteam.dynamic.config.starter.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author purgeyao
 * @since 1.0
 */
@Configuration
public class DynamicConfigListenerConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DynamicConfigListenerConfiguration.class);

    @Bean
    public DynamicConfigApplicationListener actionApplicationListener(ContextRefresher contextRefresher,
                                                                      PropertyUtil propertyUtil) {
        log.info("[DynamicConfigApplicationListener] DynamicConfigApplicationListener listener on");
        return new DynamicConfigApplicationListener(contextRefresher, propertyUtil);
    }

}
