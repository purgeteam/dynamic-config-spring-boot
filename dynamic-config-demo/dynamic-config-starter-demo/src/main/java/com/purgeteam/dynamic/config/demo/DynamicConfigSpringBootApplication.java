package com.purgeteam.dynamic.config.demo;

import com.purgeteam.dynamic.config.starter.annotation.EnableDynamicConfigEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDynamicConfigEvent
@SpringBootApplication
public class DynamicConfigSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(DynamicConfigSpringBootApplication.class, args);
  }

}
