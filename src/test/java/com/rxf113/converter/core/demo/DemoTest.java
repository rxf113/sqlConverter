package com.rxf113.converter.core.demo;

import com.rxf113.converter.core.converter.DefaultMySqlConverterFactory;
import com.rxf113.converter.core.converter.MysqlConverter;
import com.rxf113.converter.core.converter.MysqlConverterPrintProxy;
import com.rxf113.converter.core.enums.ControlType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author rxf113
 */
public class DemoTest {

    @Test
    public void addConditionTest() {
        String sqlStr = "select name , math_score , chinese_score , eng_score from student_score where a = 90";
        List<String> conditions = Arrays.asList("a=8", "c like '%fff%'");

        boolean res = new Demo().addConditionTest(sqlStr, conditions);
        assertTrue(res);
    }

    @Test
    public void fieldsControlTest() {
        String sqlStr = "select name , math_score , chinese_score , eng_score from student_score where a = 90";

        Map<String, String> fieldsControlMap = new HashMap<>(2, 1);
        //排除此表字段
        fieldsControlMap.put("student_score", "name,math_score");
        boolean res = new Demo().fieldsControlTest(sqlStr, fieldsControlMap);
        assertTrue(res);
    }

    @Test
    public void testBoth() {

        String sqlStr = "select name , math_score , chinese_score , eng_score from student_score where a = 90";

        List<String> conditions = Arrays.asList("a=8", "c like '%fff%'");

        Map<String, String> fieldsControlMap = new HashMap<>(2, 1);
        //排除此表字段
        fieldsControlMap.put("student_score", "name,math_score");

        boolean res = new Demo().testBoth(sqlStr, conditions, fieldsControlMap);
        assertTrue(res);
    }
}