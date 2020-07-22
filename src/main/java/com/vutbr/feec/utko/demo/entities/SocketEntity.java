package com.vutbr.feec.utko.demo.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class SocketEntity implements Serializable {

    private Long id;
    private boolean userInHome;
    private String state;
    private BigDecimal consumption;
    private String consumptionUnit;
    private BigDecimal current;
    private String currentUnit;
    private BigDecimal voltage;
    private String voltageUnit;
    private BigDecimal power;
    private String powerUnit;
    private Timestamp recordTimestamp;
    private String groupId;
    private String deviceId;
    private String homeId;
    private String gatewayId;

    public SocketEntity() {
    }

    public SocketEntity(Long id, boolean userInHome, String state, BigDecimal consumption, String consumptionUnit, BigDecimal current, String currentUnit, BigDecimal voltage, String voltageUnit, BigDecimal power, String powerUnit, Timestamp recordTimestamp, String groupId, String deviceId, String homeId, String gatewayId) {
        this.id = id;
        this.userInHome = userInHome;
        this.state = state;
        this.consumption = consumption;
        this.consumptionUnit = consumptionUnit;
        this.current = current;
        this.currentUnit = currentUnit;
        this.voltage = voltage;
        this.voltageUnit = voltageUnit;
        this.power = power;
        this.powerUnit = powerUnit;
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

    public BigDecimal getConsumption() {
        return consumption;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
    }

    public BigDecimal getCurrent() {
        return current;
    }

    public void setCurrent(BigDecimal current) {
        this.current = current;
    }

    public BigDecimal getVoltage() {
        return voltage;
    }

    public void setVoltage(BigDecimal voltage) {
        this.voltage = voltage;
    }

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
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

    public String getConsumptionUnit() {
        return consumptionUnit;
    }

    public void setConsumptionUnit(String consumptionUnit) {
        this.consumptionUnit = consumptionUnit;
    }

    public String getCurrentUnit() {
        return currentUnit;
    }

    public void setCurrentUnit(String currentUnit) {
        this.currentUnit = currentUnit;
    }

    public String getVoltageUnit() {
        return voltageUnit;
    }

    public void setVoltageUnit(String voltageUnit) {
        this.voltageUnit = voltageUnit;
    }

    public String getPowerUnit() {
        return powerUnit;
    }

    public void setPowerUnit(String powerUnit) {
        this.powerUnit = powerUnit;
    }
}
