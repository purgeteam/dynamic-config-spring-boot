# dynamic-config-spring-boot

[![Maven Central](https://img.shields.io/maven-central/v/com.purgeteam/dynamic-config-spring-boot-starter.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:com.purgeteam%20AND%20a:dynamic-config-spring-boot-starter)
![License](https://img.shields.io/badge/SpringBoot-2.1.8RELEASE-green.svg)
![License](https://img.shields.io/badge/JAVA-1.8+-green.svg)
![License](https://img.shields.io/badge/maven-3.0+-green.svg)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

qq交流群:`812321371`

## 简介:
- 配置监听器

## 功能使用

> 添加依赖

```
<dependency>
  <groupId>com.purge</groupId>
  <artifactId>dynamic-config-spring-boot-starter</artifactId>
  <version>0.1.0.RELEASE</version>
</dependency>
```

### 1. @EnableDynamicConfigEvent

> 简介：开启这个特性注解，具备配置推送更新监听能力。

> 使用：

启动类添加`@EnableDynamicConfigEvent` 注解。
```
@EnableDynamicConfigEvent
@SpringBootApplication
public class DynamicConfigSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(DynamicConfigSpringBootApplication.class, args);
  }

}
```

编写事件接受器：

创建`NacosListener`实现`ApplicationListener<ActionConfigEvent>`

```
@Slf4j
@Component
public class NacosListener implements ApplicationListener<ActionConfigEvent> {

  @Override
  public void onApplicationEvent(ActionConfigEvent event) {
    log.info("接收事件");
    log.info(event.getPropertyMap().toString());
  }
}
```

在`onApplicationEvent`方法里获取目标值，作相应的逻辑处理。

`ActionConfigEvent` 主要包含 `Map<String, HashMap> propertyMap;`,propertyMap结构如下:

```
{
    `被更新的配置key`:{
        before: `原来的值`，
        after: `更新后的值`
    },
    `被更新的配置key`:{
        before: `原来的值`，
        after: `更新后的值`
    }
}
```
