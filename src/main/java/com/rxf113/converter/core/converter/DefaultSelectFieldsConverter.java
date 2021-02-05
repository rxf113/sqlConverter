package com.rxf113.converter.core.converter;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitorAdapter;
import com.rxf113.converter.core.unit.Table;
import com.rxf113.converter.core.control.FieldsControl;
import com.rxf113.converter.core.visitors.CusSelectFieldVisitorAdapter;
import com.rxf113.converter.core.visitors.CusSelectSQLASTVisitorAdapterImpl;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 字段查询转换器
 *
 * @author rxf113
 */
public class DefaultSelectFieldsConverter {

    private SQLStatement sqlStatement;

    private CusSelectFieldVisitorAdapter sqlAstVisitorAdapter;

    private SQLASTOutputVisitor sqlAstOutputVisitor;

    private StringBuilder outSql = new StringBuilder(32);


    public DefaultSelectFieldsConverter(CusSelectFieldVisitorAdapter sqlAstVisitorAdapter, SQLASTOutputVisitor sqlAstOutputVisitor) {
        this.sqlAstVisitorAdapter = sqlAstVisitorAdapter;
        this.sqlAstOutputVisitor = sqlAstOutputVisitor;
    }

    public DefaultSelectFieldsConverter() {
        sqlAstVisitorAdapter = new CusSelectSQLASTVisitorAdapterImpl();
        sqlAstOutputVisitor = new MySqlOutputVisitor(outSql);
    }

    public String convertSql(String sql,List<FieldsControl> fieldsControls){
        DefaultSelectFieldsConverter converter = new DefaultSelectFieldsConverter();
        converter.initSqlStatement(sql);

        List<Table> tables = converter.getTables();

        Map<Integer, List<String>> assembledFields = converter.findAssembledFields(fieldsControls, tables);
        converter.resolveFields(assembledFields);
        return converter.getConvertedSql();
    }


    private void initSqlStatement(String sqlStr) {
        SQLStatementParser parser = new MySqlStatementParser(sqlStr);
        this.sqlStatement = parser.parseStatement();
    }

    public String getConvertedSql(){
        sqlStatement.accept(sqlAstOutputVisitor);
        return outSql.toString();
    }

    /**
     * 表信息
     *
     * @param visitorAdapter visitor
     * @return void
     **/
    public List<Table> getTables(CusSelectFieldVisitorAdapter visitorAdapter) {
        sqlStatement.accept(visitorAdapter);
        return visitorAdapter.getTables();
    }

    public List<Table> getTables() {
        return getTables(sqlAstVisitorAdapter);
    }


    public void resolveFields(Map<Integer, List<String>> assembledFields, CusSelectFieldVisitorAdapter visitorAdapter) {
        visitorAdapter.setAssembledFields(assembledFields);
        sqlStatement.accept(visitorAdapter);
    }

    public void resolveFields(Map<Integer, List<String>> assembledFields) {
        resolveFields(assembledFields, this.sqlAstVisitorAdapter);
    }

    /**
     * 根据字段控制和表信息 组装字段
     *
     * @param fieldsControls 字段控制集
     * @param tables         表信息
     * @return Map<Integer, List < String>> key:type  val:组装字段集
     **/
    public Map<Integer, List<String>> findAssembledFields(List<FieldsControl> fieldsControls, List<Table> tables) {
        Map<Integer, List<FieldsControl>> fieldsControlMap = fieldsControls.stream().peek(fieldsControl -> {
            String tableName = fieldsControl.getTableName();
            //根据表名找到表信息
            List<Table> tableList = tables.stream().filter(table -> table.getTableName().equals(tableName)).collect(Collectors.toList());
            //源fieldsControl
            List<String> originFields = fieldsControl.getFields();
            List<String> treatedFields = new ArrayList<>(originFields);
            //遍历table组装field
            tableList.forEach(table -> {
                List<String> cuTreatedFields = originFields.stream().map(field -> table.getAlias() == null ? field : String.format("%s.%s", table.getAlias(), field)).collect(Collectors.toList());
                treatedFields.addAll(cuTreatedFields);
            });
            fieldsControl.setFields(treatedFields);
        }).collect(Collectors.groupingBy(FieldsControl::getType));

        Map<Integer, List<String>> resultMap = new HashMap<>(2, 1);
        fieldsControlMap.forEach((k, v) -> {
            resultMap.put(k, v.stream().flatMap(i -> i.getFields().stream()).distinct().collect(Collectors.toList()));
        });
        return resultMap;
    }
}
