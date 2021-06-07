package com.rxf113.converter.core.demo;

import com.rxf113.converter.core.converter.DefaultConverterFactory;
import com.rxf113.converter.core.converter.MysqlConverter;
import com.rxf113.converter.core.enums.ControlType;
import com.rxf113.converter.core.processor.AddConditionVisitorProcessor;
import com.rxf113.converter.core.processor.FieldsControlProcessor;
import com.rxf113.converter.core.visitor.AddConditionCusVisitorAdapter;
import com.rxf113.converter.core.visitor.FieldsControlVisitorAdapter;
import com.rxf113.converter.core.visitor.GetTableNameAliasVisitorAdapter;

import java.util.*;

/**
 * @author rxf113
 */
public class Demo {
    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.testBoth();
        //demo.fieldsControlTest();
    }

    public void addConditionTest() {
        String sqlStr = "select name , math_score , chinese_score , eng_score from student_score where a = 90";

        List<String> conditions = Arrays.asList("a=8", "c like '%fff%'");

        MysqlConverter mysqlConverter = DefaultConverterFactory.getDefaultConverter(ControlType.ADD_CONDITION, conditions);
        String convert = mysqlConverter.convert(sqlStr);
        System.out.println(convert);
    }

    public void fieldsControlTest() {
        //====================限制字段测试====================

        String sqlStr = "select name , math_score , chinese_score , eng_score from student_score where a = 90";

        Map<String, String> fieldsControlMap = new HashMap<>(2, 1);
        //排除此表字段
        fieldsControlMap.put("student_score", "name,math_score");

        MysqlConverter mysqlConverter = DefaultConverterFactory.getDefaultConverter(ControlType.FIELDS_CONTROL, fieldsControlMap);
        String convertedSql = mysqlConverter.convert(sqlStr);
        System.out.println(convertedSql);
    }

    public void testBoth(){
        String sqlStr = "select name , math_score , chinese_score , eng_score from student_score where a = 90";


        List<String> conditions = Arrays.asList("a=8", "c like '%fff%'");



        Map<String, String> fieldsControlMap = new HashMap<>(2, 1);
        //排除此表字段
        fieldsControlMap.put("student_score", "name,math_score");

        MysqlConverter mysqlConverter = DefaultConverterFactory.getDefaultConverter(ControlType.ALL, conditions, fieldsControlMap);
        String convert = mysqlConverter.convert(sqlStr);
        System.out.println(convert);
    }
}
