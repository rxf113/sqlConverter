package com.rxf113.converter.core.visitor;

import com.alibaba.druid.sql.visitor.SQLASTVisitor;

/**
 * 自定义visitor适配
 *
 * @author rxf113
 */
public interface CusVisitorAdapter<T> extends SQLASTVisitor {

    /**
     * druid api 的visit
     *
     * @param expr 表达式
     * @return true
     */
    boolean visit(T expr);
}
