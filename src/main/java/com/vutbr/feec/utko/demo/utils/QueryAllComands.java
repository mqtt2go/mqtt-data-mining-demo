package com.vutbr.feec.utko.demo.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QueryAllComands {

    private String type;
    private long timestamp;
    @JsonProperty("command_type")
    private String commandType;
    private String value;

    public QueryAllComands() {
        this.type = "command";
        this.timestamp = System.currentTimeMillis();
        this.commandType = "query_all";
        this.value = "all";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "QueryAllComands{" +
                "type='" + type + '\'' +
                ", timestamp=" + timestamp +
                ", commandType='" + commandType + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
