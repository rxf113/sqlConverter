package com.rxf113.converter.core;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObjectImpl;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTVisitorAdapter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rxf113
 */
public class DefaultSelectConverter implements Converter<SelectSqlObjectService>{

    private List<TableControl> controlList;

    {
        controlList = new ArrayList<>();
        controlList.add(new TableControl("ff", Collections.singletonList("d")));
    }

    @Override
    public SelectSqlObjectService sqlConvertObj(String sqlStr) {
        SQLStatementParser parser = new MySqlStatementParser(sqlStr);
        SQLStatement sqlStatement = parser.parseStatement();
        SelectSQLASTVisitorAdapterImpl visitorAdapter = new SelectSQLASTVisitorAdapterImpl();
        sqlStatement.accept(visitorAdapter);
        return visitorAdapter;
    }

    private String getResolvedSql(List<String> fields,SQLSelectQueryBlock sqlSelectQueryBlock){
        sqlSelectQueryBlock.getSelectList().removeIf(i -> {
            SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr) i.getExpr();
            String name = sqlIdentifierExpr.getName();
            return fields.contains(name);
        });
        return sqlSelectQueryBlock.toString();
    }

    private String createField(Table table){
        String field;
        String alias = table.getAlias();
        String tableName = table.getTableName();
        if (table.getAlias() != null){
            field = tableName;
        }else {
            field = String.format("%s.%s",alias,tableName);
        }
        return field;
    }

    static class SelectSQLASTVisitorAdapterImpl extends SQLASTVisitorAdapter implements SelectSqlObjectService{

        private SQLObjectImpl sqlSelectQueryBlock;
        private SQLObjectImpl sqlExprTableSource;

        @Override
        public boolean visit(SQLExprTableSource x) {
            sqlExprTableSource = x;
            return true;
        }

        @Override
        public boolean visit(SQLSelectQueryBlock x) {
            sqlSelectQueryBlock = x;
            //x.getSelectList()
//            List<SQLSelectItem> selectItemList = x.getSelectList();
//            selectItemList.remove(1);
//            selectItemList.forEach(selectItem -> {
//                System.out.println("attr:" + selectItem.getAttributes());
//                SQLExpr expr = selectItem.getExpr();
//                System.out.println("expr:" + SQLUtils.toMySqlString(selectItem.getExpr()));
//            });
//            System.out.println("table:" + SQLUtils.toMySqlString(x.getFrom()));
            //System.out.println("where:" + SQLUtils.toMySqlString(x.getWhere()));
            //System.out.println("order by:" + SQLUtils.toMySqlString(x.getOrderBy().getItems().get(0)));
            //System.out.println("limit:" + SQLUtils.toMySqlString(x.getLimit()));

            return true;
        }

        @Override
        public SQLObjectImpl getSelectQueryBlock() {
            return this.sqlSelectQueryBlock;
        }

        @Override
        public SQLObjectImpl getExprTableSource() {
            return this.sqlExprTableSource;
        }
    }

    static class TableControl implements Comparable{
        public TableControl(String tableName, List<String> fields) {
            this.tableName = tableName;
            this.fields = fields;
        }

        private String tableName;
        private List<String> fields;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public List<String> getFields() {
            return fields;
        }

        public void setFields(List<String> fields) {
            this.fields = fields;
        }

        @Override
        public int compareTo(Object o) {
            return 0;
        }
    }

    static class Table{
        public Table(String tableName, String alias) {
            this.tableName = tableName;
            this.alias = alias;
        }

        private String tableName;
        private String alias;

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }
    }
}
