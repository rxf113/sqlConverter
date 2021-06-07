package com.rxf113.converter.cus.processor;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.rxf113.converter.core.unit.Table;
import com.rxf113.converter.cus.visitor.CusVisitorAdapter;
import com.rxf113.converter.cus.visitor.GetTableNameAliasVisitorAdapter;

import java.util.List;

/**
 * 控制查询字段
 *
 * @author rxf113
 */
public class FieldsControlProcessor implements VisitorProcessor {

    private GetTableNameAliasVisitorAdapter getTableNameAliasVisitorAdapter;

    @Override
    public void process(SQLStatement statement) {
        statement.accept(getTableNameAliasVisitorAdapter);
        List<Table> tables = getTableNameAliasVisitorAdapter.getTables();

        //根据tables 匹配字段

    }
}
