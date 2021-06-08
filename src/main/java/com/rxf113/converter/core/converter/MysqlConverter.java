package com.rxf113.converter.core.converter;

import com.rxf113.converter.core.processor.VisitorProcessor;
import com.rxf113.converter.core.statement.CusStatementParser;
import com.rxf113.converter.core.statement.MySQLCusStatementParser;
import com.rxf113.converter.core.visitor.output.CusMysqlOutPutVisitor;

import java.util.List;

/**
 * @author rxf113
 */
public class MysqlConverter extends AbstractConverter {

    public MysqlConverter(CusStatementParser statementParser, List<VisitorProcessor> processors) {
        this.statementParser = statementParser;
        this.processors = processors;
        this.cusOutPutVisitor = new CusMysqlOutPutVisitor(new StringBuilder());
    }

    public MysqlConverter(List<VisitorProcessor> processors) {
        this.statementParser = new MySQLCusStatementParser();
        this.processors = processors;
        this.cusOutPutVisitor = new CusMysqlOutPutVisitor(new StringBuilder());
    }

}
