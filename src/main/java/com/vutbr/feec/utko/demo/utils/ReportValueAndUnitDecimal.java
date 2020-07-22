package com.vutbr.feec.utko.demo.utils;

import java.math.BigDecimal;

public class ReportValueAndUnitDecimal {
    private BigDecimal value;
    private String unit;

    public ReportValueAndUnitDecimal() {
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
