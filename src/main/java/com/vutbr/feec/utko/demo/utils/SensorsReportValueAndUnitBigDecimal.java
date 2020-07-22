package com.vutbr.feec.utko.demo.utils;

public class SensorsReportValueAndUnitBigDecimal {

    private long timestamp;
    private ReportValueAndUnitDecimal report;

    public SensorsReportValueAndUnitBigDecimal() {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ReportValueAndUnitDecimal getReport() {
        return report;
    }

    public void setReport(ReportValueAndUnitDecimal report) {
        this.report = report;
    }
}
