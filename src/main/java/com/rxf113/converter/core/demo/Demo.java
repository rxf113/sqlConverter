package com.rxf113.converter.core.demo;

import com.rxf113.converter.core.converter.MysqlConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rxf113
 */
public class Demo {
    public static void main(String[] args) {
        //限制字段测试
        String sqlStr = "select name , math_score , chinese_score , eng_score from student_score where a = 90";

        Map<String, String> fieldsControlMap = new HashMap<>(2, 1);
        //排除此表字段
        fieldsControlMap.put("student_score", "name,math_score");

        MysqlConverter defaultMysqlConverter = MysqlConverter.defaultMysqlConverter(fieldsControlMap);

        String convertedSql = defaultMysqlConverter.convert(sqlStr);
        System.out.println(convertedSql);

    }
}
