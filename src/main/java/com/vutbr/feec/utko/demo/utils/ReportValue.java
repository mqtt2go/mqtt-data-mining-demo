package com.vutbr.feec.utko.demo.utils;

public class ReportValue {

    private String value;

    public ReportValue() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ReportValue{" +
                "value='" + value + '\'' +
                '}';
    }
}
