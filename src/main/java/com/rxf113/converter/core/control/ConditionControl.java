package com.rxf113.converter.core.control;

import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;

/**
 * 条件控制
 *
 * @author rxf113
 */
public class ConditionControl {
    private String tableName;
    private String field;
    private SQLBinaryOperator operator;
    private Object value;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SQLBinaryOperator getOperator() {
        return operator;
    }

    public void setOperator(SQLBinaryOperator operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
