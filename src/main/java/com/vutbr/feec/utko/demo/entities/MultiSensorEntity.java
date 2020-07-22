package com.vutbr.feec.utko.demo.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class MultiSensorEntity {

    private Long id;
    private boolean userInHome;
    private String state;
    private BigDecimal temperature;
    private BigDecimal humidity;
    private String motion;
    private String smoke;
    private String water;
    private Timestamp recordTimestamp;
    private String groupId;
    private String deviceId;
    private String homeId;
    private String gatewayId;

    public MultiSensorEntity() {
    }

    public MultiSensorEntity(Long id, boolean userInHome, String state, BigDecimal temperature, BigDecimal humidity, String motion, String smoke, String water, Timestamp recordTimestamp, String groupId, String deviceId, String homeId, String gatewayId) {
        this.id = id;
        this.userInHome = userInHome;
        this.state = state;
        this.temperature = temperature;
        this.humidity = humidity;
        this.motion = motion;
        this.smoke = smoke;
        this.water = water;
        this.recordTimestamp = recordTimestamp;
        this.groupId = groupId;
        this.deviceId = deviceId;
        this.homeId = homeId;
        this.gatewayId = gatewayId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isUserInHome() {
        return userInHome;
    }

    public void setUserInHome(boolean userInHome) {
        this.userInHome = userInHome;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public BigDecimal getHumidity() {
        return humidity;
    }

    public void setHumidity(BigDecimal humidity) {
        this.humidity = humidity;
    }

    public String getMotion() {
        return motion;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
    }

    public Timestamp getRecordTimestamp() {
        return recordTimestamp;
    }

    public void setRecordTimestamp(Timestamp recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }
}
