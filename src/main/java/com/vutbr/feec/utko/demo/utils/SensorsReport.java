package com.vutbr.feec.utko.demo.utils;

public class SensorsReport {

    private long timestamp;
    private String report;

    public SensorsReport() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
