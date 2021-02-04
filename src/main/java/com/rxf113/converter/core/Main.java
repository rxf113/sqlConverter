package com.rxf113.converter.core;

import com.alibaba.druid.sql.ast.SQLObjectImpl;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.rxf113.converter.core.control.FieldsControl;

import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author rxf113
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        DefaultSelectConverter converter = new DefaultSelectConverter();
        converter.initSQLStatement("select (select qwe , wwe , eer from ff) as jb, a , b, c, f.d from ff f");
        DefaultSelectConverter.SelectSQLASTVisitorAdapterImpl selectSQLASTVisitorAdapter = new DefaultSelectConverter.SelectSQLASTVisitorAdapterImpl();
        List<Table> tables = converter.traverseAST(selectSQLASTVisitorAdapter);

        //限制条件
        FieldsControl control = new FieldsControl();
        control.setTableName("ff");
        ;
        control.setFields(Collections.singletonList("d"));
        //no
        control.setType(1);


        FieldsControl control2 = new FieldsControl();
        control2.setTableName("ff");
        ;
        control2.setFields(new ArrayList<>(Arrays.asList("a", "b")));
        //en
        control2.setType(2);

        List<FieldsControl> fieldsControls = new ArrayList<>(Arrays.asList(control,control2));


        //根据限制条件和table信息 返回需要排除的字段
        Map<Integer, List<String>> treatedFields = findTreatedFields(fieldsControls, tables);
        converter.resolveFields(treatedFields);


        StringWriter out = new StringWriter();
        DefaultSelectConverter.CusOutPut outputVisitor = new DefaultSelectConverter.CusOutPut(out);
        converter.traverseAST(outputVisitor);
        System.out.println(out.toString());
    }

    private static String convertSqlByControl(String originSql,FieldsControl fieldsControl){

        return null;
    }


    /**
     * @param treatedFields 处理过的字段集
     * @param selectQueryBlock selectQueryBlock
     * @return java.lang.String
     **/
    private static String getResolvedSql(Map<Integer, List<String>> treatedFields, SQLSelectQueryBlock selectQueryBlock) {
        List<SQLSelectItem> selectList = selectQueryBlock.getSelectList();
        //移除 限制集 里面的
        selectList.removeIf(i -> Optional.ofNullable(treatedFields.get(1)).map(fields -> fields.contains(i.getExpr().toString())).orElse(false));
        //移除非限制 外面的
        selectList.removeIf(i -> Optional.ofNullable(treatedFields.get(2)).map(fields -> !fields.contains(i.getExpr().toString())).orElse(false));
        return selectQueryBlock.toString();
    }


    /**
     * @param fieldsControls 字段控制集
     * @param tables         表信息
     * @return Map<Integer, List < String>> key:type  val:组装字段集
     **/
    private static Map<Integer, List<String>> findTreatedFields(List<FieldsControl> fieldsControls, List<Table> tables) {
        Map<Integer, List<FieldsControl>> fieldsControlMap = fieldsControls.stream().peek(fieldsControl -> {
            String tableName = fieldsControl.getTableName();
            //根据表名找到表信息
            Optional<Table> tableOptional = tables.stream().filter(table -> table.getTableName().equals(tableName)).findAny();
            //源fieldsControl
            List<String> originFields = fieldsControl.getFields();
            //有别名 拼接别名
            List<String> treatedFields = originFields.stream().map(field -> tableOptional.
                    map(k -> k.getAlias() == null ? field : String.format("%s.%s", k.getAlias(), field)).orElse(field))
                    .collect(Collectors.toList());
            treatedFields.addAll(fieldsControl.getFields());
            fieldsControl.setFields(treatedFields);
        }).collect(Collectors.groupingBy(FieldsControl::getType));

        Map<Integer, List<String>> resultMap = new HashMap<>(2, 1);
        fieldsControlMap.forEach((k, v) -> {
            resultMap.put(k, v.stream().flatMap(i -> i.getFields().stream()).distinct().collect(Collectors.toList()));
        });
        return resultMap;
    }
}
