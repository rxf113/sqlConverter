package com.rxf113.converter.core.converter;

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
