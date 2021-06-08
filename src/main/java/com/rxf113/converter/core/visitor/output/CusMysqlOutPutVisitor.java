package com.rxf113.converter.core.visitor.output;

import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;

/**
 * @author rxf113
 */
public class CusMysqlOutPutVisitor extends AbstractCusOutPutVisitor {

    public CusMysqlOutPutVisitor(StringBuilder sb) {
        this.sb = sb;
        this.outputVisitor = new MySqlOutputVisitor(sb);
    }

    public void resetSb() {
        this.sb = new StringBuilder();
    }
}
