package com.rxf113.converter.core.converter;

import com.rxf113.converter.core.enums.ControlType;
import com.rxf113.converter.core.processor.AddConditionVisitorProcessor;
import com.rxf113.converter.core.processor.FieldsControlProcessor;
import com.rxf113.converter.core.visitor.AddConditionCusVisitorAdapter;
import com.rxf113.converter.core.visitor.FieldsControlVisitorAdapter;
import com.rxf113.converter.core.visitor.GetTableNameAliasVisitorAdapter;

import java.util.*;

/**
 * @author rxf113
 */
public class DefaultConverterFactory {

    @SuppressWarnings("unchecked")
    public static MysqlConverter getDefaultConverter(ControlType controlType, Object... controlObj) {
        if (controlObj.length == 1) {
            switch (controlType) {
                case ADD_CONDITION:
                    AddConditionCusVisitorAdapter addConditionCusVisitorAdapter = new AddConditionCusVisitorAdapter();
                    addConditionCusVisitorAdapter.setConditions(castObj(controlObj[0], List.class));
                    AddConditionVisitorProcessor addConditionVisitorProcessor = new AddConditionVisitorProcessor(addConditionCusVisitorAdapter);
                    return new MysqlConverter(Collections.singletonList(addConditionVisitorProcessor));
                case FIELDS_CONTROL:
                    GetTableNameAliasVisitorAdapter getTableNameAliasVisitorAdapter = new GetTableNameAliasVisitorAdapter();
                    FieldsControlVisitorAdapter fieldsControlVisitorAdapter = new FieldsControlVisitorAdapter();
                    FieldsControlProcessor visitorProcessor = new FieldsControlProcessor(getTableNameAliasVisitorAdapter, fieldsControlVisitorAdapter);
                    visitorProcessor.setControlObj(castObj(controlObj[0], Map.class));
                    return new MysqlConverter(Collections.singletonList(visitorProcessor));
                default:
                    throw new RuntimeException("输入有误");
            }
        } else {
            AddConditionCusVisitorAdapter addConditionCusVisitorAdapter = new AddConditionCusVisitorAdapter();
            addConditionCusVisitorAdapter.setConditions(castObj(controlObj[0], List.class));
            AddConditionVisitorProcessor addConditionVisitorProcessor = new AddConditionVisitorProcessor(addConditionCusVisitorAdapter);
            GetTableNameAliasVisitorAdapter getTableNameAliasVisitorAdapter = new GetTableNameAliasVisitorAdapter();
            FieldsControlVisitorAdapter fieldsControlVisitorAdapter = new FieldsControlVisitorAdapter();
            FieldsControlProcessor visitorProcessor = new FieldsControlProcessor(getTableNameAliasVisitorAdapter, fieldsControlVisitorAdapter);
            visitorProcessor.setControlObj(castObj(controlObj[1], Map.class));
            return new MysqlConverter(Arrays.asList(visitorProcessor, addConditionVisitorProcessor));
        }

    }

    public static <T> T castObj(Object obj, Class<T> t) {
        try {
            return t.cast(obj);
        } catch (ClassCastException e) {
            throw new RuntimeException("类型不匹配");
        }
    }
}
