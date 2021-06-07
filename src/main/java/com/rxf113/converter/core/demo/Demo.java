package com.rxf113.converter.core.demo;

import com.rxf113.converter.core.converter.MysqlConverter;
import com.rxf113.converter.core.processor.AddConditionVisitorProcessor;

import java.util.*;

/**
 * @author rxf113
 */
public class Demo {
    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.addConditionTest();
        //demo.fieldsControlTest();


    }

    public void addConditionTest() {
        String sqlStr = "select name , math_score , chinese_score , eng_score from student_score where a = 90";

        List<String> conditions = Arrays.asList("a=8", "c like '%fff%'");
        AddConditionVisitorProcessor addConditionVisitorProcessor = new AddConditionVisitorProcessor();
        addConditionVisitorProcessor.setControlObj(conditions);
        MysqlConverter mysqlConverter = new MysqlConverter(Collections.singletonList(addConditionVisitorProcessor));
        String convert = mysqlConverter.convert(sqlStr);
        System.out.println(convert);
    }

    public void fieldsControlTest() {
        //====================限制字段测试====================

        String sqlStr = "select name , math_score , chinese_score , eng_score from student_score where a = 90";

        Map<String, String> fieldsControlMap = new HashMap<>(2, 1);
        //排除此表字段
        fieldsControlMap.put("student_score", "name,math_score");

        MysqlConverter defaultMysqlConverter = MysqlConverter.defaultMysqlConverter(fieldsControlMap);

        String convertedSql = defaultMysqlConverter.convert(sqlStr);
        System.out.println(convertedSql);
    }
}
