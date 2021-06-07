package com.rxf113.converter.core.processor;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.rxf113.converter.core.visitor.AddConditionCusVisitorAdapter;

import java.util.List;
/**
 * 添加条件
 *
 * @author rxf113
 */
public class AddConditionVisitorProcessor implements VisitorProcessor {

    private AddConditionCusVisitorAdapter addConditionCusVisitorAdapter;

    public AddConditionVisitorProcessor(AddConditionCusVisitorAdapter addConditionCusVisitorAdapter) {
        this.addConditionCusVisitorAdapter = addConditionCusVisitorAdapter;
    }

    @Override
    public void process(SQLStatement statement) {
        statement.accept(addConditionCusVisitorAdapter);
    }
}
