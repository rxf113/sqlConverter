package com.rxf113.converter.core.converter;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.rxf113.converter.core.processor.VisitorProcessor;
import com.rxf113.converter.core.statement.CusStatementParser;
import com.rxf113.converter.core.visitor.output.CusOutPutVisitor;

import java.util.List;

/**
 * 抽象转换
 *
 * @author rxf113
 */
public abstract class AbstractConverter implements Converter {

    /**
     * 自定义解析器
     */
    protected CusStatementParser statementParser;

    /**
     * processor集合
     */
    protected List<VisitorProcessor> processors;

    /**
     * OutPutVisitor
     */
    protected CusOutPutVisitor cusOutPutVisitor;

    @Override
    public String convert(String sql) {
        CusStatementParser statementParser = this.statementParser;
        List<VisitorProcessor> processors = this.processors;

        SQLStatement sqlStatement = statementParser.parseStatement(sql);
        for (VisitorProcessor processor : processors) {
            processor.process(sqlStatement);
        }
        SQLASTOutputVisitor outputVisitor = cusOutPutVisitor.getOutputVisitor();
        sqlStatement.accept(outputVisitor);
        return cusOutPutVisitor.getSql();
    }
}
