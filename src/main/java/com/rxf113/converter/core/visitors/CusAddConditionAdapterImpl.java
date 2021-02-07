package com.rxf113.converter.core.visitors;

import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.visitor.SQLASTVisitorAdapter;
import com.rxf113.converter.core.control.ConditionControl;
import com.rxf113.converter.core.unit.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义条件添加实现类
 *
 * @author rxf113
 */
public class CusAddConditionAdapterImpl extends SQLASTVisitorAdapter implements CusConditionVisitorAdapter<Map<String, ConditionControl>> {

    private Map<String, ConditionControl> assembledFields;

    private List<Table> tables = new ArrayList<>();

    @Override
    public List<Table> getTables() {
        return this.tables;
    }

    @Override
    public void setControlInfo(Map<String, ConditionControl> assembledFields) {
        this.assembledFields = assembledFields;
    }

    @Override
    public boolean visit(SQLExprTableSource x) {
        String tableName = x.getName().getSimpleName();
        String alias = x.getAlias();
        tables.add(new Table(tableName, alias));
        return true;
    }


    @Override
    public boolean visit(SQLSelectQueryBlock x) {

        if (assembledFields == null || assembledFields.size() == 0) {
            return true;
        }
        List<SQLSelectItem> selectItemList = x.getSelectList();
        for (SQLSelectItem sqlSelectItem : selectItemList) {
            String expr = sqlSelectItem.getExpr().toString();
            ConditionControl conditionControl = assembledFields.get(expr);
            //当前查询字段中存在此字段
            if (conditionControl != null) {
                //并且有这张表
                SQLTableSource from = x.getFrom();
                SQLTableSource tableSource = from.findTableSource(conditionControl.getTableName());
                if (tableSource != null) {
                    SQLBinaryOpExpr sqlBinaryOpExpr = new SQLBinaryOpExpr(new SQLIdentifierExpr(expr), conditionControl.getOperator(), new SQLIdentifierExpr(conditionControl.getValue().toString()));
                    x.addCondition(sqlBinaryOpExpr);
                }
            }
        }
        return true;
    }
}
