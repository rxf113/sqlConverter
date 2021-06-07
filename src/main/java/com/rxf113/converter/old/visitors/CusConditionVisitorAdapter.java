package com.rxf113.converter.old.visitors;

import com.alibaba.druid.sql.visitor.SQLASTVisitor;
import com.rxf113.converter.core.model.Table;

import java.util.List;

/**
 * 自定义的接口 扩展两个方法
 *
 * @author rxf113
 */
public interface CusConditionVisitorAdapter<T> extends SQLASTVisitor {
    /**
     * 获取表信息
     * @return 表信息
     */
    List<Table> getTables();

    /**
     * 设置组装字段集
     * @param assembledFields 字段集
     */
    void setControlInfo(T assembledFields);
}