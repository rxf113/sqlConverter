package com.rxf113.converter.core.statement;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;

/**
 * Mysql语句解析器
 *
 * @author rxf113
 */
public class MySQLCusStatementParser implements CusStatementParser {
    @Override
    public SQLStatement parseStatement(String sql) {
        MySqlStatementParser statementParser = new MySqlStatementParser(sql);
        return statementParser.parseStatement();
    }
}
