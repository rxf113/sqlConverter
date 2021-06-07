package com.rxf113.converter.old.converter;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.rxf113.converter.core.model.Table;
import com.rxf113.converter.old.visitors.CusConditionVisitorAdapter;

import java.util.List;

/**
 * 字段查询转换器
 *
 * @author rxf113
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AbstractConverter<K, T> {

    private SQLStatement sqlStatement;

    private CusConditionVisitorAdapter conditionVisitorAdapter;

    private SQLASTOutputVisitor sqlAstOutputVisitor;

    private StringBuilder outSql = new StringBuilder(32);


    public AbstractConverter(CusConditionVisitorAdapter sqlAstVisitorAdapter, SQLASTOutputVisitor sqlAstOutputVisitor) {
        this.conditionVisitorAdapter = sqlAstVisitorAdapter;
        this.sqlAstOutputVisitor = sqlAstOutputVisitor;
    }

    public AbstractConverter(CusConditionVisitorAdapter conditionVisitorAdapter) {
        this.conditionVisitorAdapter = conditionVisitorAdapter;
        this.sqlAstOutputVisitor = new MySqlOutputVisitor(outSql);
    }

    /**
     * 所有流程
     *
     * @param sql      原始sql
     * @param controls 控制集
     * @return java.lang.String
     **/
    public String convertSql(String sql, K controls) {
        initSqlStatement(sql);
        List<Table> tables = getTables();
        T assembledFields = resolveControlAndTables(tables, controls);
        resolveFields(assembledFields);
        return getConvertedSql();
    }


    /**
     * 自定义处理方法
     *
     * @param tables   表
     * @param controls 控制集
     * @return t
     */
    public abstract T resolveControlAndTables(List<Table> tables, K controls);


    private void initSqlStatement(String sqlStr) {
        SQLStatementParser parser = new MySqlStatementParser(sqlStr);
        this.sqlStatement = parser.parseStatement();
    }

    public String getConvertedSql() {
        sqlStatement.accept(sqlAstOutputVisitor);
        return outSql.toString();
    }

    /**
     * 表信息
     *
     * @param visitorAdapter visitor
     * @return void
     **/
    public List<Table> getTables(CusConditionVisitorAdapter visitorAdapter) {
        sqlStatement.accept(visitorAdapter);
        return visitorAdapter.getTables();
    }

    public List<Table> getTables() {
        return getTables(conditionVisitorAdapter);
    }


    public void resolveFields(T assembledFields, CusConditionVisitorAdapter visitorAdapter) {
        visitorAdapter.setControlInfo(assembledFields);
        sqlStatement.accept(visitorAdapter);
    }

    public void resolveFields(T assembledFields) {
        resolveFields(assembledFields, this.conditionVisitorAdapter);
    }


}
