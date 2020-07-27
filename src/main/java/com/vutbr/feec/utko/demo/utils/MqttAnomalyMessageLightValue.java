package com.vutbr.feec.utko.demo.utils;

public class MqttAnomalyMessageLightValue {

    private String eventName;
    private String message;
    private String status;

    public MqttAnomalyMessageLightValue() {
    }

    public MqttAnomalyMessageLightValue(String eventName, String message, String status) {
        this.eventName = eventName;
        this.message = message;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MqttAnomalyMessageLightValue{" +
                "eventName='" + eventName + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
