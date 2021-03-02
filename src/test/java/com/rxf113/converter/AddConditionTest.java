package com.rxf113.converter;

import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.rxf113.converter.core.control.ConditionControl;
import com.rxf113.converter.core.converter.DefaultAddConditionConverter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 动态查询条件测试
 *
 * @author rxf113
 */
public class AddConditionTest {

    @Test
    public void addConditionTest() {
        DefaultAddConditionConverter conditionConverter = new DefaultAddConditionConverter();

        ConditionControl conditionControl2 = new ConditionControl();
        conditionControl2.setField("name");
        conditionControl2.setOperator(SQLBinaryOperator.GreaterThan);
        conditionControl2.setValue(50);
        conditionControl2.setTableName("table_one");

        ConditionControl conditionControl = new ConditionControl();
        conditionControl.setField("id");
        conditionControl.setOperator(SQLBinaryOperator.Equality);
        conditionControl.setValue(3);
        conditionControl.setTableName("table_two");

        String sql = conditionConverter.convertSql("select t1.name , tt.id , tt.name ,t1.id , (select name from bbc ) as sc from table_one t1, table_two tt on tt.id = to.id  where t1.id = 1", Arrays.asList(conditionControl, conditionControl2));
        System.out.println(sql);
    }
}
