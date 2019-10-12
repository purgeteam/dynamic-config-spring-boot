package com.purgeteam.dynamic.config.starter;

import com.purgeteam.dynamic.config.starter.util.PropertyUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author purgeyao
 * @since 1.0
 */
@Configuration
public class DynamicConfigConfiguration {

    @Bean
    public PropertyUtil propertyUtil() {
        return new PropertyUtil();
    }

}
