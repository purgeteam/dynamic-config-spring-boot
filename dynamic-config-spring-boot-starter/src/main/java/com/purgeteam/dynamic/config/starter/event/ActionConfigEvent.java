package com.purgeteam.dynamic.config.starter.event;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationEvent;

/**
 * @author purgeyao
 * @since 1.0
 */
public class ActionConfigEvent extends ApplicationEvent {

    private String eventDesc;

    private Map<String, HashMap> propertyMap;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ActionConfigEvent(Object source, String eventDesc, Map<String, HashMap> propertyMap) {
        super(source);
        this.eventDesc = eventDesc;
        this.propertyMap = propertyMap;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public Map<String, HashMap> getPropertyMap() {
        return propertyMap;
    }

    public void setPropertyMap(Map<String, HashMap> propertyMap) {
        this.propertyMap = propertyMap;
    }

}
