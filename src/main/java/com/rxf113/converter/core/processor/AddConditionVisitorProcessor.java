package com.rxf113.converter.core.processor;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.rxf113.converter.core.visitor.AddConditionCusVisitorAdapter;

import java.util.List;
/**
 * 添加条件
 *
 * @author rxf113
 */
public class AddConditionVisitorProcessor extends AbstractWithControlVisitorProcessor<List<String>> {

    private AddConditionCusVisitorAdapter addConditionCusVisitorAdapter = new AddConditionCusVisitorAdapter();

    @Override
    public void setControlObj(List<String> conditions) {
        addConditionCusVisitorAdapter.setConditions(conditions);
    }

    @Override
    public void process(SQLStatement statement) {
        statement.accept(addConditionCusVisitorAdapter);
    }
}
