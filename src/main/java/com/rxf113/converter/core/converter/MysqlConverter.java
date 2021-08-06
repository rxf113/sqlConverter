package com.rxf113.converter.core.converter;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.rxf113.converter.core.processor.VisitorProcessor;
import com.rxf113.converter.core.statement.CusStatementParser;
import com.rxf113.converter.core.statement.MySQLCusStatementParser;

import java.util.List;

/**
 * @author rxf113
 */
public class MysqlConverter extends AbstractConverter {

    private final StringBuilder sb = new StringBuilder();

    /**
     * OutPutVisitor
     */
    private MySqlOutputVisitor outputVisitor = new MySqlOutputVisitor(sb);

    public MysqlConverter(CusStatementParser statementParser, List<VisitorProcessor> processors) {
        this.statementParser = statementParser;
        this.processors = processors;
    }

    public MysqlConverter(List<VisitorProcessor> processors) {
        this.statementParser = new MySQLCusStatementParser();
        this.processors = processors;
    }


    @Override
    public String getOutputSql(SQLStatement sqlStatement) {
        sqlStatement.accept(outputVisitor);
        String res = sb.toString();
        sb.delete(0, sb.length());
        return res;
    }
}
