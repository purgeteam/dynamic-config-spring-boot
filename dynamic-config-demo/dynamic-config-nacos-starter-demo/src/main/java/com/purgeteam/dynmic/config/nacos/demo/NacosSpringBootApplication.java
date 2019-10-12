package com.purgeteam.dynmic.config.nacos.demo;

import com.purgeteam.dynamic.config.starter.annotation.EnableDynamicConfigEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDynamicConfigEvent
@SpringBootApplication
public class NacosSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(NacosSpringBootApplication.class, args);
  }

}
