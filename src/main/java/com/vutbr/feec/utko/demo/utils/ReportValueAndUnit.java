package com.vutbr.feec.utko.demo.utils;

public class ReportValueAndUnit {
    private String value;
    private String unit;

    public ReportValueAndUnit() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "ReportValueAndUnit{" +
                "value='" + value + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
