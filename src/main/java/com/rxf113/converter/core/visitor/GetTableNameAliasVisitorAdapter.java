package com.rxf113.converter.core.visitor;

import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.rxf113.converter.core.model.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rxf113
 */
public class GetTableNameAliasVisitorAdapter implements CusVisitorAdapter<SQLExprTableSource> {

    List<Table> tables = new ArrayList<>();

    public List<Table> getTables() {
        return this.tables;
    }


    @Override
    public boolean visit(SQLExprTableSource x) {
        String tableName = x.getName().getSimpleName();
        String alias = x.getAlias();
        tables.add(new Table(tableName, alias));
        return true;
    }


}