package com.rxf113.converter.cus.processor;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.rxf113.converter.cus.visitor.CusVisitorAdapter;

public interface VisitorProcessor {
    void process(SQLStatement statement);

    //void setControl();
}
