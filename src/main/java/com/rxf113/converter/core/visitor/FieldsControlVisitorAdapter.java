package com.rxf113.converter.core.visitor;

import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;

import java.util.HashSet;
import java.util.List;

/**
 * 限制查询字段的visit适配
 *
 * @author rxf113
 */
public class FieldsControlVisitorAdapter implements CusVisitorAdapter<SQLSelectQueryBlock> {

    /**
     * 需要移除的字段
     **/
    private HashSet<String> assembledFields;

    public void setAssembledFields(HashSet<String> assembledFields) {
        this.assembledFields = assembledFields;
    }

    @Override
    public boolean visit(SQLSelectQueryBlock expr) {
        List<SQLSelectItem> selectList = expr.getSelectList();
        if (selectList == null || selectList.size() == 0) {
            return true;
        }
        //移除 需要移除的字段
        selectList.removeIf(i -> assembledFields.contains(i.toString()));
        return true;
    }
}
