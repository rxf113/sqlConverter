package com.rxf113.converter.core.demo;

import com.rxf113.converter.core.converter.DefaultMySqlConverterFactory;
import com.rxf113.converter.core.converter.MysqlConverter;
import com.rxf113.converter.core.converter.MysqlConverterPrintProxy;
import com.rxf113.converter.core.enums.ControlType;

import java.util.List;
import java.util.Map;

/**
 * @author rxf113
 */
public class Demo {

    public boolean addConditionTest(String sqlStr, List<String> conditions) {
        MysqlConverter mysqlConverter = DefaultMySqlConverterFactory.getDefaultConverter(ControlType.ADD_CONDITION, conditions);
        MysqlConverterPrintProxy converterPrintProxy = new MysqlConverterPrintProxy(mysqlConverter);
        converterPrintProxy.convert(sqlStr);
        return true;
    }

    public boolean fieldsControlTest(String sqlStr, Map<String, String> fieldsControlMap) {
        //排除此表字段
        fieldsControlMap.put("student_score", "name,math_score");

        MysqlConverter mysqlConverter = DefaultMySqlConverterFactory.getDefaultConverter(ControlType.FIELDS_CONTROL, fieldsControlMap);
        MysqlConverterPrintProxy converterPrintProxy = new MysqlConverterPrintProxy(mysqlConverter);
        converterPrintProxy.convert(sqlStr);
        return true;
    }

    public boolean testBoth(String sqlStr, List<String> conditions, Map<String, String> fieldsControlMap) {
        MysqlConverter mysqlConverter = DefaultMySqlConverterFactory.getDefaultConverter(ControlType.ALL, conditions, fieldsControlMap);
        MysqlConverterPrintProxy converterPrintProxy = new MysqlConverterPrintProxy(mysqlConverter);
        converterPrintProxy.convert(sqlStr);
        return true;
    }
}
