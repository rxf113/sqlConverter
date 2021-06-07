package com.rxf113.converter.old.control;

import com.rxf113.converter.old.enums.FieldControlTypeEnum;

import java.util.List;

/**
 * 查询字段控制
 *
 * @author rxf113
 */
public class FieldsControl {
    private String tableName;

    private List<String> fields;
    private FieldControlTypeEnum fieldControlTypeEnum;

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

    public FieldControlTypeEnum getFieldControlTypeEnum() {
        return fieldControlTypeEnum;
    }

    public void setFieldControlTypeEnum(FieldControlTypeEnum fieldControlTypeEnum) {
        this.fieldControlTypeEnum = fieldControlTypeEnum;
    }
}
