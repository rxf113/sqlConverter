package com.rxf113.converter.core.visitor;

import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;

/**
 * @author rxf113
 */
public class AddConditionCusVisitorAdapter implements CusVisitorAdapter<SQLSelectQueryBlock> {
    @Override
    public boolean visit(SQLSelectQueryBlock expr) {
        return false;
    }
}
