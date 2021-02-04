package com.rxf113.converter.core.control;

import java.util.List;

/**
 * @author rxf113
 */
public class FieldsControl {
    private String tableName;

    private List<String> fields;
    private int type;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
