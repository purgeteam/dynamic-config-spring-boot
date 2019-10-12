package com.purgeteam.dynamic.config.starter.annotation;

import com.purgeteam.dynamic.config.starter.DynamicConfigListenerConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * {@link DynamicConfigListenerConfiguration} 开启配置变化监听器
 *
 * @author purgeyao
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(DynamicConfigListenerConfiguration.class)
public @interface EnableDynamicConfigEvent {

}
