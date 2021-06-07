package com.rxf113.converter.core.converter;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.rxf113.converter.core.processor.AbstractWithControlVisitorProcessor;
import com.rxf113.converter.core.processor.FieldsControlProcessor;
import com.rxf113.converter.core.processor.VisitorProcessor;
import com.rxf113.converter.core.statement.CusStatementParser;
import com.rxf113.converter.core.statement.MySQLCusStatementParser;
import com.rxf113.converter.core.visitor.FieldsControlVisitorAdapter;
import com.rxf113.converter.core.visitor.GetTableNameAliasVisitorAdapter;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author rxf113
 */
public class MysqlConverter extends AbstractConverter {


    public MysqlConverter(CusStatementParser statementParser,
                          List<VisitorProcessor> processors) {
        super(statementParser, processors);
    }

    public MysqlConverter(
            List<VisitorProcessor> processors) {
        super(new MySQLCusStatementParser(), processors);
    }

    @Override
    public String convert(String sql) {
        CusStatementParser statementParser = getStatementParser();
        List<VisitorProcessor> processors = getProcessors();

        SQLStatement sqlStatement = statementParser.parseStatement(sql);
        for (VisitorProcessor processor : processors) {
            processor.process(sqlStatement);
        }
        StringBuilder sb = new StringBuilder();
        MySqlOutputVisitor mySqlOutputVisitor = new MySqlOutputVisitor(sb);
        sqlStatement.accept(mySqlOutputVisitor);
        //打印结果sql
        return sb.toString();
    }

    public static MysqlConverter defaultMysqlConverter(Map<String, String> controlObj) {
        CusStatementParser statementParser = new MySQLCusStatementParser();
        GetTableNameAliasVisitorAdapter getTableNameAliasVisitorAdapter = new GetTableNameAliasVisitorAdapter();
        FieldsControlVisitorAdapter fieldsControlVisitorAdapter = new FieldsControlVisitorAdapter();
        FieldsControlProcessor visitorProcessor = new FieldsControlProcessor(getTableNameAliasVisitorAdapter, fieldsControlVisitorAdapter);
        visitorProcessor.setControlObj(controlObj);
        return new MysqlConverter(statementParser, Collections.singletonList(visitorProcessor));
    }

    public static void main(String[] args) {

    }
}
