package com.vutbr.feec.utko.demo.utils;

public class MqttAnomalyMessageLight {

    private String message;
    private long timestamp;

    public MqttAnomalyMessageLight() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
