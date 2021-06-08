package com.rxf113.converter.core.converter;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.rxf113.converter.core.processor.VisitorProcessor;
import com.rxf113.converter.core.statement.CusStatementParser;

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

    @Override
    public String convert(String sql) {
        CusStatementParser statementParser = this.statementParser;
        List<VisitorProcessor> processors = this.processors;

        SQLStatement sqlStatement = statementParser.parseStatement(sql);
        for (VisitorProcessor processor : processors) {
            processor.process(sqlStatement);
        }
        StringBuilder sb = new StringBuilder();
        MySqlOutputVisitor mySqlOutputVisitor = new MySqlOutputVisitor(sb);
        sqlStatement.accept(mySqlOutputVisitor);
        return sb.toString();
    }
}
