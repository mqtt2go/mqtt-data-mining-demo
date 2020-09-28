package com.vutbr.feec.utko.demo.utils;

public class MqttAnomalyMessageLight {

    private int priorityLevel;
    private long timestamp;
    private String type;
    private MqttAnomalyMessageLightValue value;

    public MqttAnomalyMessageLight() {
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public MqttAnomalyMessageLightValue getValue() {
        return value;
    }

    public void setValue(MqttAnomalyMessageLightValue value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MqttAnomalyMessageLight{" +
                "priorityLevel=" + priorityLevel +
                ", timestamp=" + timestamp +
                ", type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}
