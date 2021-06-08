package com.rxf113.converter.core.visitor.output;

import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;

/**
 * @author rxf113
 */
public interface CusOutPutVisitor {
    SQLASTOutputVisitor getOutputVisitor();

    String getSql();
}
