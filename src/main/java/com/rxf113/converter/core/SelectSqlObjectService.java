package com.rxf113.converter.core;

import com.alibaba.druid.sql.ast.SQLObjectImpl;

/**
 * @author rxf113
 */
public interface SelectSqlObjectService {
    SQLObjectImpl getSelectQueryBlock();
    SQLObjectImpl getExprTableSource();
    // private SQLObjectImpl sqlSelectQueryBlock;
    //        private SQLObjectImpl sqlExprTableSource;
}
