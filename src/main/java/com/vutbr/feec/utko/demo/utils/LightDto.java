package com.vutbr.feec.utko.demo.utils;

import java.time.LocalDateTime;

public class LightDto {

    private Long id;
    private LocalDateTime timestampRecord;
    private String value;
    private String lightId;

    public LightDto() {
    }

    public LightDto(Long id, LocalDateTime timestampRecord, String value, String lightId) {
        this.id = id;
        this.timestampRecord = timestampRecord;
        this.value = value;
        this.lightId = lightId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestampRecord() {
        return timestampRecord;
    }

    public void setTimestampRecord(LocalDateTime timestampRecord) {
        this.timestampRecord = timestampRecord;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLightId() {
        return lightId;
    }

    public void setLightId(String lightId) {
        this.lightId = lightId;
    }
}
