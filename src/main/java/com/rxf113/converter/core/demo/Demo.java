package com.rxf113.converter.core.demo;

import com.rxf113.converter.core.converter.DefaultMySqlConverterFactory;
import com.rxf113.converter.core.converter.MysqlConverter;
import com.rxf113.converter.core.converter.MysqlConverterPrintProxy;
import com.rxf113.converter.core.enums.ControlType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rxf113
 */
public class Demo {
    public static void main(String[] args) {
        Demo demo = new Demo();
        //demo.testBoth();
        demo.addConditionTest();
        //demo.fieldsControlTest();
    }

    public void addConditionTest() {
        String sqlStr = "select name , math_score , chinese_score , eng_score from student_score where a = 90";

        List<String> conditions = Arrays.asList("a=8", "c like '%fff%'");

        MysqlConverter mysqlConverter = DefaultMySqlConverterFactory.getDefaultConverter(ControlType.ADD_CONDITION, conditions);
        MysqlConverterPrintProxy converterPrintProxy = new MysqlConverterPrintProxy(mysqlConverter);
        converterPrintProxy.convert(sqlStr);
    }

    public void fieldsControlTest() {
        //====================限制字段测试====================

        String sqlStr = "select name , math_score , chinese_score , eng_score from student_score where a = 90";

        Map<String, String> fieldsControlMap = new HashMap<>(2, 1);
        //排除此表字段
        fieldsControlMap.put("student_score", "name,math_score");

        MysqlConverter mysqlConverter = DefaultMySqlConverterFactory.getDefaultConverter(ControlType.FIELDS_CONTROL, fieldsControlMap);
        MysqlConverterPrintProxy converterPrintProxy = new MysqlConverterPrintProxy(mysqlConverter);
        converterPrintProxy.convert(sqlStr);
    }

    public void testBoth(){
        String sqlStr = "select name , math_score , chinese_score , eng_score from student_score where a = 90";


        List<String> conditions = Arrays.asList("a=8", "c like '%fff%'");



        Map<String, String> fieldsControlMap = new HashMap<>(2, 1);
        //排除此表字段
        fieldsControlMap.put("student_score", "name,math_score");

        MysqlConverter mysqlConverter = DefaultMySqlConverterFactory.getDefaultConverter(ControlType.ALL, conditions, fieldsControlMap);
        MysqlConverterPrintProxy converterPrintProxy = new MysqlConverterPrintProxy(mysqlConverter);
        converterPrintProxy.convert(sqlStr);
    }
}
