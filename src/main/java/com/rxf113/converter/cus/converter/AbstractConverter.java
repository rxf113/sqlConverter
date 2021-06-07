package com.rxf113.converter.cus.converter;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.rxf113.converter.cus.processor.VisitorProcessor;
import com.rxf113.converter.cus.statement.CusStatementParser;
import com.rxf113.converter.cus.visitor.CusVisitorAdapter;

import java.util.List;

public class AbstractConverter implements Converter{

    private CusStatementParser statementParser;

    private List<VisitorProcessor> processors;


    @Override
    public String convert(String sql) {
        SQLStatement sqlStatement = statementParser.parseStatement(sql);
        for (VisitorProcessor processor : processors) {
            processor.process(sqlStatement);
        }
        return null;
    }


}
