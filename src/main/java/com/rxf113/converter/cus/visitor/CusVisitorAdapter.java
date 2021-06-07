package com.rxf113.converter.cus.visitor;

import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

import java.util.List;

public interface CusVisitorAdapter<T> extends SQLASTVisitor {
    boolean visit(T expr);

   // void setHandle(List<Handle<T>> handles);
}
