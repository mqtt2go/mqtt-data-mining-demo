package com.vutbr.feec.utko.demo.utils.mappings;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceIdDeviceTypeMappingRoot {

    @JsonProperty("type")
    private String type;
    @JsonProperty("report_type")
    private String reportType;
    @JsonProperty("value")
    private DeviceIdDeviceTypeMapping deviceIdDeviceTypeMapping;

    public DeviceIdDeviceTypeMappingRoot() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public DeviceIdDeviceTypeMapping getDeviceIdDeviceTypeMapping() {
        return deviceIdDeviceTypeMapping;
    }

    public void setDeviceIdDeviceTypeMapping(DeviceIdDeviceTypeMapping deviceIdDeviceTypeMapping) {
        this.deviceIdDeviceTypeMapping = deviceIdDeviceTypeMapping;
    }

    @Override
    public String toString() {
        return "DeviceIdDeviceTypeMappingRoot{" +
                "type='" + type + '\'' +
                ", reportType='" + reportType + '\'' +
                ", deviceIdDeviceTypeMapping=" + deviceIdDeviceTypeMapping +
                '}';
    }
}
