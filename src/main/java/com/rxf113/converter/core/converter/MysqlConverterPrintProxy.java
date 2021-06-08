package com.rxf113.converter.core.converter;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.rxf113.converter.core.enums.ControlType;
import com.rxf113.converter.core.processor.VisitorProcessor;
import com.rxf113.converter.core.statement.CusStatementParser;
import com.rxf113.converter.core.statement.MySQLCusStatementParser;
import com.rxf113.converter.core.visitor.output.CusMysqlOutPutVisitor;
import com.rxf113.converter.core.visitor.output.CusOutPutVisitor;

import java.util.List;

/**
 * 打印sql代理
 *
 * @author rxf113
 */
public class MysqlConverterPrintProxy implements Converter {

    public MysqlConverterPrintProxy(MysqlConverter converter) {
        this.converter = converter;
    }

    private MysqlConverter converter;

    public void setConverter(MysqlConverter converter) {
        this.converter = converter;
    }

    /**
     * 打印输入输出sql
     *
     * @param sql originSql
     * @return convertedSql
     **/
    @Override
    public String convert(String sql) {

        System.out.println("originSql : " + sql);
        String convertedSql = converter.convert(sql);
        System.out.println("===============================");
        System.out.println("convertedSql : " + convertedSql);

        return convertedSql;
    }
}
