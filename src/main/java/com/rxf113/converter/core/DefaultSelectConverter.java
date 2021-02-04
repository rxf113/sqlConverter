package com.rxf113.converter.core;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObjectImpl;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLQueryExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlBinlogStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTVisitorAdapter;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author rxf113
 */
public class DefaultSelectConverter{

    private SQLStatement sqlStatement;

    private SelectSQLASTVisitorAdapterImpl visitorAdapter;

    public void initSQLStatement(String sqlStr) {
        SQLStatementParser parser = new MySqlStatementParser(sqlStr);
        this.sqlStatement = parser.parseStatement();
    }

    /**
     * 遍历指定的ast
     *
     * @param
     * @return void
     **/
    public List<Table> traverseAST(SelectSQLASTVisitorAdapterImpl visitorAdapter){
        this.visitorAdapter = visitorAdapter;
        sqlStatement.accept(visitorAdapter);
        return visitorAdapter.getTables();
    }

    /**
     * 遍历普通ast
     *
     * @param
     * @return void
     **/
    public void traverseAST(SQLASTVisitorAdapter visitorAdapter){
        sqlStatement.accept(visitorAdapter);
    }

    public void resolveFields(Map<Integer, List<String>> treatedFields,SelectSQLASTVisitorAdapterImpl visitorAdapter){
        visitorAdapter.setTreatedFields(treatedFields);
        sqlStatement.accept(visitorAdapter);
    }

    public void resolveFields(Map<Integer, List<String>> treatedFields){
        resolveFields(treatedFields,this.visitorAdapter);
    }

    static class CusOutPut extends MySqlOutputVisitor {

        public CusOutPut(Appendable appender) {
            super(appender);
        }

        public CusOutPut(Appendable appender, boolean parameterized) {
            super(appender, parameterized);
        }

//        @Override
//        public boolean visit(SQLSelectQueryBlock x) {
//            List<SQLSelectItem> selectList = x.getSelectList();
////            for (SQLSelectItem sqlSelectItem : selectList) {
////                visit(sqlSelectItem);
////            }
//            //System.out.println( " ffff : " + x.toString());
//            if(selectList.size() == 0){
//
//                //print0(null);
//            }
//            return true;
//        }
//
//        @Override
//        public boolean visit(SQLSelectItem x) {
//            return true;
//        }
//
//        @Override
//        public boolean visit(SQLQueryExpr x) {
//            //x.getSubQuery()
//            //visit(x.getQueryBlock());
//            return true;
//        }
    }

    /**
     * 遍历ast
     **/
    static class SelectSQLASTVisitorAdapterImpl extends SQLASTVisitorAdapter {

        private Map<Integer, List<String>> treatedFields;

        public SelectSQLASTVisitorAdapterImpl() {
        }

        public void setTreatedFields(Map<Integer, List<String>> treatedFields) {
            this.treatedFields = treatedFields;
        }

        public SelectSQLASTVisitorAdapterImpl(Map<Integer, List<String>> treatedFields) {
            this.treatedFields = treatedFields;
        }

        private List<Table> tables = new ArrayList<>();

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


        @Override
        public boolean visit(SQLSelectQueryBlock x) {
            if(treatedFields == null){
                return true;
            }
            //x.getSelectList()
            List<SQLSelectItem> selectItemList = x.getSelectList();
            //移除
            selectItemList.removeIf(i -> i.getExpr() instanceof SQLIdentifierExpr && Optional.ofNullable(treatedFields.get(1)).map(fields -> fields.contains(i.getExpr().toString())).orElse(false));


            selectItemList.removeIf(i -> i.getExpr() instanceof SQLIdentifierExpr &&
                    Optional.ofNullable(treatedFields.get(2)).map(fields -> !fields.contains(i.getExpr().toString())).orElse(false));

            return true;
        }
    }
}
