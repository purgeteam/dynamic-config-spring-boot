package com.purgeteam.dynamic.config.demo;

import com.purgeteam.dynamic.config.starter.event.ActionConfigEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author purgeyao
 * @since 1.0
 */
@Slf4j
@Component
public class NacosListener implements ApplicationListener<ActionConfigEvent> {

  @Override
  public void onApplicationEvent(ActionConfigEvent event) {
    log.info("接收事件");
    log.info(event.getPropertyMap().toString());
  }
}
