package com.rxf113.converter;

import com.rxf113.converter.core.control.FieldsControl;
import com.rxf113.converter.core.converter.DefaultSelectFieldsConverter;
import com.rxf113.converter.core.enums.FieldControlTypeEnum;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * 动态查询字段测试
 *
 * @author rxf113
 */

public class SelectFieldsTest {

    @Test
    public void kk() {
        int i = new Random().nextInt(180);
        String b1D23A456A = LCS("1A2C3D4B56", "B1D23A456A");
        System.out.println(b1D23A456A);
    }

    public String LCS(String s1, String s2) {
        // write code here
        char[] ch1 = s1.toCharArray();
        char[] ch2 = s2.toCharArray();
        Map<String, String> dpMap = new HashMap<>();

        String res = "";
        for (int i = 0; i < ch1.length; i++) {
            for (int j = 0; j < ch2.length; j++) {
                if (ch1[i] == ch2[j]) {
                    String vv = dpMap.get(String.format("%d,%d", i, j));
                    String vb = vv == null ? String.valueOf(ch1[i]) : vv + ch1[i];
                    dpMap.put(String.format("%d,%d", i + 1, j + 1), vb);


                } else {
                    String v1 = dpMap.get(String.format("%d,%d", i - 1, j));
                    String v2 = dpMap.get(String.format("%d,%d", i, j - 1));
                    String v;
                    if (v1 == null && v2 == null) {
                        v = null;
                    } else {
                        if (v1 == null) {
                            v = v2;
                        } else if (v2 == null) {
                            v = v1;
                        } else {
                            v = v1.length() > v2.length() ? v1 : v2;
                        }
                    }
                    dpMap.put(String.format("%d,%d", i + 1, j + 1), v);
                }
            }
        }
        return dpMap.get(String.format("%d,%d", ch1.length, ch2.length));
    }

    @Test
    public void testSelectFields() {

        //排除的字段
        FieldsControl control = new FieldsControl();
        control.setTableName("di");
        control.setFields(Collections.singletonList("id"));
        //no
        control.setFieldControlTypeEnum(FieldControlTypeEnum.EXCLUDE);

        //包含的字段
        FieldsControl control2 = new FieldsControl();
        control2.setTableName("ff");
        control2.setFields(new ArrayList<>(Arrays.asList("a", "b")));
        //en
        control2.setFieldControlTypeEnum(FieldControlTypeEnum.INCLUDE);

        //控制集
        List<FieldsControl> fieldsControls = new ArrayList<>(Arrays.asList(control, control2));

        //原始sql
        String originSql = "select count(id), (select a,id,c from jkl) as df,di.id from di_two dt join di di ON dt.parent_id = di.parent_id where di.id = 10 \n" +
                "GROUP BY di.id ";

        DefaultSelectFieldsConverter selectFieldsConverter = new DefaultSelectFieldsConverter();
        String convertedSql = selectFieldsConverter.convertSql(originSql, fieldsControls);
        System.out.println("原始sql: " + originSql);
        System.out.println("\n排除字段: " + control.getFields());
        System.out.println("\n保留字段: " + control2.getFields());
        System.out.println("\n转换后的sql: " + convertedSql);
    }
}
