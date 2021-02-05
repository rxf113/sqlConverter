package com.rxf113.converter.core.unit;

/**
 * 表的基本信息
 *
 * @author rxf113
 */
public class Table {
    public Table(String tableName, String alias) {
        this.tableName = tableName;
        this.alias = alias;
    }

    private String tableName;
    private String alias;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
