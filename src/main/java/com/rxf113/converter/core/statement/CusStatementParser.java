package com.rxf113.converter.core.statement;

import com.alibaba.druid.sql.ast.SQLStatement;

/**
 * 解析器
 *
 * @author rxf113
 */
public interface CusStatementParser {
    /**
     * 解析器 原始sql->SQLStatement
     *
     * @param sql 原始sql
     * @return SQLStatement
     */
    SQLStatement parseStatement(String sql);
}
