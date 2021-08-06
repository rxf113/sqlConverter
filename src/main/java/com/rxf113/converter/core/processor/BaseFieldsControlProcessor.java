package com.rxf113.converter.core.processor;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.rxf113.converter.core.model.Table;
import com.rxf113.converter.core.visitor.FieldsControlVisitorAdapter;
import com.rxf113.converter.core.visitor.GetTableNameAliasVisitorAdapter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 基础实现 支持Map<String, String>是控制集
 *
 * @author rxf113
 */
public class BaseFieldsControlProcessor extends AbstractFieldsControlProcessor<Map<String, String>, HashSet<String>> {

    protected GetTableNameAliasVisitorAdapter getTableNameAliasVisitorAdapter;

    protected FieldsControlVisitorAdapter fieldsControlVisitorAdapter;

    public BaseFieldsControlProcessor(GetTableNameAliasVisitorAdapter getTableNameAliasVisitorAdapter, FieldsControlVisitorAdapter fieldsControlVisitorAdapter, Map<String, String> controlObj) {
        this.getTableNameAliasVisitorAdapter = getTableNameAliasVisitorAdapter;
        this.fieldsControlVisitorAdapter = fieldsControlVisitorAdapter;
        this.controlObj = controlObj;
    }


    @Override
    public void process(SQLStatement statement) {
        statement.accept(getTableNameAliasVisitorAdapter);
        //获取表名及别名
        List<Table> tables = getTableNameAliasVisitorAdapter.getTables();

        //组装需要排除的字段 , 表名-字段 & 别名-字段
        HashSet<String> assembledFields = findAssembledFields(tables, controlObj);

        //根据表名别名组装字段 order.name  o.name
        fieldsControlVisitorAdapter.setAssembledFields(assembledFields);
        statement.accept(fieldsControlVisitorAdapter);
    }

    @Override
    public HashSet<String> findAssembledFields(List<Table> tables, Map<String, String> controlObjMap) {
        HashSet<String> assembledFields = new HashSet<>();
        tables.forEach(table -> {
            //table
            String tableName = table.getTableName();
            //t
            String alias = table.getAlias();
            String fields = controlObjMap.get(tableName);
            if (fields != null) {
                Arrays.stream(fields.split(",")).forEach(i -> {
                    assembledFields.add(String.format("%s.%s", tableName, i));
                    assembledFields.add(String.format("%s.%s", alias, i));
                    assembledFields.add(i);
                });
            }
        });
        return assembledFields;
    }
}
