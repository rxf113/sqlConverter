package com.rxf113.converter.core.converter;

import com.rxf113.converter.core.control.ConditionControl;
import com.rxf113.converter.core.unit.Table;
import com.rxf113.converter.core.visitors.CusAddConditionAdapterImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author rxf113
 */
public class DefaultAddConditionConverter extends AbstractConverter<List<ConditionControl>, Map<String, ConditionControl>> {

    public DefaultAddConditionConverter() {
        super(new CusAddConditionAdapterImpl());
    }

    @Override
    public Map<String, ConditionControl> resolveControlAndTables(List<Table> tables, List<ConditionControl> controls) {
        return resolve(tables, controls);
    }

    public Map<String, ConditionControl> resolve(List<Table> tables, List<ConditionControl> conditionControls) {
        Map<String, ConditionControl> map = new HashMap<>(conditionControls.size() * 2 + 1, 1);
        for (ConditionControl conditionControl : conditionControls) {
            String tableName = conditionControl.getTableName();
            String field = conditionControl.getField();
            //此表名的table
            List<Table> tablesOfThisName = tables.stream().filter(i -> i.getTableName().equals(tableName)).collect(Collectors.toList());
            for (Table table : tablesOfThisName) {
                if (table.getAlias() != null) {
                    map.put(String.format("%s.%s", table.getAlias(), field), conditionControl);
                }
                map.put(field, conditionControl);
            }
        }
        return map;
    }
}
