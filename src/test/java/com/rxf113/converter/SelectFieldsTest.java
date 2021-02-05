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
//public ListNode addTwoNumbers(ListNode l1,ListNode l2){
//        // 2 4 3
//        // 5 6 4
//        int flagA = 1;
//        int flagB = 1;
//        int ca = 0;
//        ListNode resNode = null;
//        while (true){
//            int cuA = 0;
//            int cuB = 0;
//            if(l1 == null ){
//                flagA--;
//            }else {
//                cuA = l1.val;
//                l1 = l1.next;
//            }
//            if(l2 == null ){
//                flagB--;
//            }else {
//                cuB = l2.val;
//                l2 = l2.next;
//            }
//            if(flagA == 0 && flagB == 0){
//                break;
//            }
//            int tempSum = cuA + cuB + ca;
//            int val = tempSum % 10;
//            ca = tempSum/10;
//            if(resNode == null){
//                resNode = new ListNode(val);
//            }else {
//                createNode(val,resNode);
//            }
//        }
//        return resNode;
//    }
}
