package com.rxf113.converter.core.processor;

import com.alibaba.druid.sql.ast.SQLStatement;

/**
 * processor
 *
 * @author rxf113
 */
public interface VisitorProcessor {
    /**
     * 根据自定义visitor 处理SQLStatement
     *
     * @param statement statement
     */
    void process(SQLStatement statement);
}
