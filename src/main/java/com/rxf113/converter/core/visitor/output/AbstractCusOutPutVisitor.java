package com.rxf113.converter.core.visitor.output;

import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;

/**
 * @author rxf113
 */
public abstract class AbstractCusOutPutVisitor implements CusOutPutVisitor {

    protected StringBuilder sb;

    protected SQLASTOutputVisitor outputVisitor;

    @Override
    public SQLASTOutputVisitor getOutputVisitor() {
        return this.outputVisitor;
    }

    @Override
    public String getSql() {
        return this.sb.toString();
    }
}
