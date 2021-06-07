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

    private static CusStatementParser statementParser = new MySQLCusStatementParser();

    public MysqlConverter(CusStatementParser statementParser,
                          List<VisitorProcessor> processors) {
        super(statementParser, processors);
    }

    public MysqlConverter(
            List<VisitorProcessor> processors) {
        super(statementParser, processors);
    }

}
