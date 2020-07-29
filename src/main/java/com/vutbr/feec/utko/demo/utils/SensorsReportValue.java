package com.vutbr.feec.utko.demo.utils;

public class SensorsReportValue {
    private long timestamp;
    private ReportValue report;

    public SensorsReportValue() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ReportValue getReport() {
        return report;
    }

    public void setReport(ReportValue report) {
        this.report = report;
    }
}
