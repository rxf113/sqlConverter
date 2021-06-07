package com.rxf113.converter.core.converter;

import com.rxf113.converter.core.processor.VisitorProcessor;
import com.rxf113.converter.core.statement.CusStatementParser;
import com.rxf113.converter.core.statement.MySQLCusStatementParser;

import java.util.List;

/**
 * @author rxf113
 */
public class MysqlConverter extends AbstractConverter {

    private static CusStatementParser statementParser = new MySQLCusStatementParser();

    public MysqlConverter(CusStatementParser statementParser,
                          List<VisitorProcessor> processors) {
        super(statementParser, processors);
    }

    public MysqlConverter(
            List<VisitorProcessor> processors) {
        super(statementParser, processors);
    }

}
