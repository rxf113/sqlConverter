package com.rxf113.converter.core.visitors;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.expr.SQLQueryExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.visitor.SQLASTVisitorAdapter;
import com.rxf113.converter.core.unit.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * 自定义字段查询实现类
 *
 * @author rxf113
 */
public class CusSelectSQLASTVisitorAdapterImpl extends SQLASTVisitorAdapter implements CusSelectFieldVisitorAdapter {

    private Map<Integer, List<String>> assembledFields;

    public CusSelectSQLASTVisitorAdapterImpl() {
    }

    @Override
    public void setAssembledFields(Map<Integer, List<String>> assembledFields) {
        this.assembledFields = assembledFields;
    }

    public void s(Map<Integer, List<String>> treatedFields) {
        this.assembledFields = treatedFields;
    }

    public CusSelectSQLASTVisitorAdapterImpl(Map<Integer, List<String>> treatedFields) {
        this.assembledFields = treatedFields;
    }

    private List<Table> tables = new ArrayList<>();

    @Override
    public List<Table> getTables() {
        return this.tables;
    }

    @Override
    public boolean visit(SQLExprTableSource x) {
        String tableName = x.getName().getSimpleName();
        String alias = x.getAlias();
        tables.add(new Table(tableName, alias));
        return true;
    }

    private boolean checkSqlSelectQueryBlock(SQLSelectQueryBlock x) {
        List<SQLSelectItem> selectList = x.getSelectList();
        return selectList.size() > 0;
    }


    @Override
    public boolean visit(SQLSelectQueryBlock x) {
        if (assembledFields == null) {
            return true;
        }
        //x.getSelectList()
        List<SQLSelectItem> selectItemList = x.getSelectList();
        if (selectItemList.size() == 0) {
            return true;
        }


        //移除限制
        selectItemList.removeIf(i -> (i.getExpr() instanceof SQLIdentifierExpr || i.getExpr() instanceof SQLPropertyExpr) && Optional.ofNullable(assembledFields.get(1)).map(fields -> fields.contains(i.getExpr().toString())).orElse(false));

        //移除非 access
        selectItemList.removeIf(i -> (i.getExpr() instanceof SQLIdentifierExpr || i.getExpr() instanceof SQLPropertyExpr) &&
                Optional.ofNullable(assembledFields.get(2)).map(fields -> !fields.contains(i.getExpr().toString())).orElse(false));

        //清除空子查询
        selectItemList.removeIf(sqlSelectItem -> {
            boolean flag = false;
            SQLExpr expr = sqlSelectItem.getExpr();
            if (expr instanceof SQLQueryExpr) {
                SQLQueryExpr queryExpr = (SQLQueryExpr) expr;
                flag = checkSqlSelectQueryBlock(queryExpr.getSubQuery().getQueryBlock());
            }
            return flag;
        });
        return true;
    }
}