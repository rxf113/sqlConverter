package com.rxf113.converter.core.converter;

import com.rxf113.converter.core.control.FieldsControl;
import com.rxf113.converter.core.enums.FieldControlTypeEnum;
import com.rxf113.converter.core.unit.Table;
import com.rxf113.converter.core.visitors.CusSelectSQLASTVisitorAdapterImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rxf113
 */
public class DefaultSelectFieldsConverter extends AbstractConverter<List<FieldsControl>, Map<FieldControlTypeEnum, List<String>>> {

    public DefaultSelectFieldsConverter() {
        super(new CusSelectSQLASTVisitorAdapterImpl());
    }

    @Override
    public Map<FieldControlTypeEnum, List<String>> resolveControlAndTables(List<Table> tables, List<FieldsControl> controls) {
        return findAssembledFields(controls, tables);
    }

    /**
     * 根据字段控制和表信息 组装字段
     *
     * @param fieldsControls 字段控制集
     * @param tables         表信息
     * @return Map<Integer, List < String>> key:type  val:组装字段集
     **/
    public Map<FieldControlTypeEnum, List<String>> findAssembledFields(List<FieldsControl> fieldsControls, List<Table> tables) {
        Map<FieldControlTypeEnum, List<FieldsControl>> fieldsControlMap = fieldsControls.stream().peek(fieldsControl -> {
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
        }).collect(Collectors.groupingBy(FieldsControl::getFieldControlTypeEnum));

        Map<FieldControlTypeEnum, List<String>> resultMap = new HashMap<>(2, 1);
        fieldsControlMap.forEach((k, v) -> {
            resultMap.put(k, v.stream().flatMap(i -> i.getFields().stream()).distinct().collect(Collectors.toList()));
        });
        return resultMap;
    }
}
