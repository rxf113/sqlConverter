package com.rxf113.converter.core.visitor;

import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;

import java.util.List;

/**
 * @author rxf113
 */
public class AddConditionCusVisitorAdapter implements CusVisitorAdapter<SQLSelectQueryBlock> {

    private List<String> conditions;

    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean visit(SQLSelectQueryBlock expr) {
        for (String condition : conditions) {
            expr.addCondition(condition);
        }
        return true;
    }

}
