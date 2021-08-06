package com.rxf113.converter.core.processor;

import com.rxf113.converter.core.model.Table;

import java.util.List;

/**
 * 控制查询字段
 * T 控制集类型
 * V visitor需要的类型
 *
 * @author rxf113
 */
public abstract class AbstractFieldsControlProcessor<T, V> implements VisitorProcessor {

    protected T controlObj;

    /**
     * 组装字段
     *
     * @param tables     tables
     * @param controlObj 控制集
     * @return V
     */
    public abstract V findAssembledFields(List<Table> tables, T controlObj);
}
