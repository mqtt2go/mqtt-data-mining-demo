package com.vutbr.feec.utko.demo.entities;

import java.io.Serializable;
import java.sql.Timestamp;

public class LightEntity implements Serializable {

    private Long id;
    private boolean userInHome;
    private String state;
    private Timestamp recordTimestamp;
    private String groupId;
    private String deviceId;
    private String homeId;
    private String gatewayId;

    public LightEntity() {
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

    public Timestamp getRecordTimestamp() {
        return recordTimestamp;
    }

    public void setRecordTimestamp(Timestamp recordTimestamp) {
        this.recordTimestamp = recordTimestamp;
    }

    @Override
    public String toString() {
        return "LightEntity{" +
                "id=" + id +
                ", userInHome=" + userInHome +
                ", state='" + state + '\'' +
                ", recordTimestamp=" + recordTimestamp +
                ", groupId='" + groupId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", homeId='" + homeId + '\'' +
                ", gatewayId='" + gatewayId + '\'' +
                '}';
    }
}
