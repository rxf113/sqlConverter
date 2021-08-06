package com.rxf113.converter.core.visitor;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.util.JdbcConstants;
import com.rxf113.converter.core.model.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rxf113
 */
public class GetTableNameAliasVisitorAdapter implements CusVisitorAdapter<SQLExprTableSource> {

    List<Table> tables = new ArrayList<>();

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

    public static void main(String[] args) {



            String sql = "update t set name = 'x' where id < 100 limit 10";
            String mysql = SQLUtils.formatMySql(sql);
            String oracle = SQLUtils.formatOracle(sql);
            System.out.println("mysql: " + mysql);
            System.out.println("oracle: " + oracle);
            String result = SQLUtils.format(sql, JdbcConstants.MYSQL);
            System.out.println(result); // 缺省大写格式

            String result_lcase = SQLUtils.format(sql
                    , JdbcConstants.MYSQL
                    , SQLUtils.DEFAULT_LCASE_FORMAT_OPTION);
            System.out.println(result_lcase); // 小写格式


    }

}