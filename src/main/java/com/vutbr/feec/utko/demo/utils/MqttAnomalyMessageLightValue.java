package com.vutbr.feec.utko.demo.utils;

public class MqttAnomalyMessageLightValue {

    private String eventName;
    private String message;

    public MqttAnomalyMessageLightValue() {
    }

    public MqttAnomalyMessageLightValue(String eventName, String message) {
        this.eventName = eventName;
        this.message = message;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MqttAnomalyMessageLightValue{" +
                "eventName='" + eventName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
