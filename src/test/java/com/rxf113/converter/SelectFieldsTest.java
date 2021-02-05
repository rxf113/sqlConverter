package com.rxf113.converter;

import com.rxf113.converter.core.control.FieldsControl;
import com.rxf113.converter.core.converter.DefaultSelectFieldsConverter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author rxf113
 */

public class SelectFieldsTest {

    @Test
    public void testSelectFields(){
        //限制字段
        FieldsControl control = new FieldsControl();
        control.setTableName("di");;
        control.setFields(Collections.singletonList("name"));
        //no
        control.setType(1);

        //access字段
        FieldsControl control2 = new FieldsControl();
        control2.setTableName("ff");
        ;
        control2.setFields(new ArrayList<>(Arrays.asList("a", "b")));
        //en
        control2.setType(1);

        List<FieldsControl> fieldsControls = new ArrayList<>(Arrays.asList(control,control2));

        String originSql = "select  GROUP_CONCAT(concat(dt.name_two,di.name) SEPARATOR '-') ,di.id from di_two dt join di di ON dt.parent_id = di.parent_id where di.id = 10 \n" +
                "GROUP BY di.id ";

        DefaultSelectFieldsConverter selectFieldsConverter = new DefaultSelectFieldsConverter();
        String s = selectFieldsConverter.convertSql(originSql, fieldsControls);
        System.out.println(s);
    }

}
