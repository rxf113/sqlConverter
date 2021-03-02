package com.rxf113.converter;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import com.alibaba.druid.util.JdbcConstants;
import com.rxf113.converter.core.control.FieldsControl;
import com.rxf113.converter.core.converter.DefaultSelectFieldsConverter;
import com.rxf113.converter.core.enums.FieldControlTypeEnum;
import com.rxf113.converter.core.visitors.CusSelectSQLASTVisitorAdapterImpl;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author rxf113
 */

public class SelectFieldsTest {

    @Test
    public void testSelectFields(){

        //排除的字段
        FieldsControl control = new FieldsControl();
        control.setTableName("di");;
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
        List<FieldsControl> fieldsControls = new ArrayList<>(Arrays.asList(control,control2));

        //原始sql
        String originSql = "select (select a,id,c from jkl) as df,di.id from di_two dt join di di ON dt.parent_id = di.parent_id where di.id = 10 \n" +
                "GROUP BY di.id ";

        DefaultSelectFieldsConverter selectFieldsConverter = new DefaultSelectFieldsConverter();
        String convertedSql = selectFieldsConverter.convertSql(originSql, fieldsControls);
        System.out.println("originSql: " + originSql);
        System.out.println("\n\nconvertedSql: " + convertedSql);
    }
}
