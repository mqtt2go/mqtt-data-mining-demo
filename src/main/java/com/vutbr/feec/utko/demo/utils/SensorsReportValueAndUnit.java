package com.vutbr.feec.utko.demo.utils;

public class SensorsReportValueAndUnit {

    private long timestamp;
    private ReportValueAndUnit report;

    public SensorsReportValueAndUnit() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ReportValueAndUnit getReport() {
        return report;
    }

    public void setReport(ReportValueAndUnit report) {
        this.report = report;
    }
}
