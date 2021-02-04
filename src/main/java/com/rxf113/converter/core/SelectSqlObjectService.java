package com.rxf113.converter.core;

import com.alibaba.druid.sql.ast.SQLObjectImpl;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;

import java.util.List;

/**
 * @author rxf113
 */
public interface SelectSqlObjectService {
    SQLSelectQueryBlock getSelectQueryBlock();

    List<Table> getTables();
    // private SQLObjectImpl sqlSelectQueryBlock;
    //        private SQLObjectImpl sqlExprTableSource;
}
