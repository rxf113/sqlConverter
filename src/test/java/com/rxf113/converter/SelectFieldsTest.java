package com.rxf113.converter;

import com.rxf113.converter.core.control.FieldsControl;
import com.rxf113.converter.core.converter.DefaultSelectFieldsConverter;
import com.rxf113.converter.core.enums.FieldControlTypeEnum;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 动态查询字段测试
 *
 * @author rxf113
 */

public class SelectFieldsTest {

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
        String originSql = "select (select a,id,c from jkl) as df,di.id from di_two dt join di di ON dt.parent_id = di.parent_id where di.id = 10 \n" +
                "GROUP BY di.id ";

        DefaultSelectFieldsConverter selectFieldsConverter = new DefaultSelectFieldsConverter();
        String convertedSql = selectFieldsConverter.convertSql(originSql, fieldsControls);
        System.out.println(convertedSql);
    }
}
