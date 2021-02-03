package com.rxf113.converter.core;

import com.alibaba.druid.sql.ast.SQLObjectImpl;

/**
 * @author rxf113
 */
public class Main {
    public static void main(String[] args) {
        DefaultSelectConverter converter = new DefaultSelectConverter();
        SelectSqlObjectService sqlObjectService = converter.sqlConvertObj("select a , b, c, d.d from ff d");
        SQLObjectImpl exprTableSource = sqlObjectService.getExprTableSource();
        SQLObjectImpl selectQueryBlock = sqlObjectService.getSelectQueryBlock();
        //根据这两个
    }
}
