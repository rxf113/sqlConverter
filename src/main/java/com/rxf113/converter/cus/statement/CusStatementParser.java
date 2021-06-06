package com.rxf113.converter.cus.statement;

import com.alibaba.druid.sql.ast.SQLStatement;

public interface CusStatementParser {
    SQLStatement parseStatement(String sql);
}
