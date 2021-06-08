package com.rxf113.converter;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLIntegerExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleOutputVisitor;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author rxf113
 */
public class test {

    static class Teacher {
        //教师类型
        public Integer teacherType;
        //教师类型字段
        public String teacherTypeField;
        //可以查看的字段
        public List<String> accessFields;
        //哪张表下 进行此操作
        public String tableName;
    }

    @Test
    public void gg() {
        StringBuilder outSql = new StringBuilder(32);

        String insertSql = "insert into tabel ('id','name') values(1,'张三')";
        OracleStatementParser parser = new OracleStatementParser(insertSql);
        SQLStatement sqlStatement = parser.parseStatement();
        OracleOutputVisitor oracleOutputVisitor = new OracleOutputVisitor(outSql);
        sqlStatement.accept(oracleOutputVisitor);
        String cs = outSql.toString().replace("\n", " ");
        System.out.println("转换后的sql : " + cs);
    }

    @Test
    public void test() {
        //select name , math_score , chinese_score , eng_score from student_score
        String sqlStr = "select name , math_score , chinese_score , eng_score from student_score where a = 90";
        System.out.printf("原始sql: %s \n\n", sqlStr);

        //以chineseTeacher为例
        Teacher chineseTeacher = new Teacher();
        chineseTeacher.teacherType = 1;
        chineseTeacher.teacherTypeField = "teacher_type";
        chineseTeacher.accessFields = new ArrayList<>(Arrays.asList("name", "chinese_score"));
        chineseTeacher.tableName = "student_score";
        //自定义的 Visitor
        AddConditionAndControlFieldsVisitor visitor = new AddConditionAndControlFieldsVisitor(chineseTeacher);

        /*
         *下面druid的api 参考  https://github.com/alibaba/druid/wiki/SQL_Parser_Demo_visitor
         *  https://github.com/alibaba/druid/wiki/Druid_SQL_AST
         */
        SQLStatementParser parser = new MySqlStatementParser(sqlStr);
        SQLStatement sqlStatement = parser.parseStatement();
        sqlStatement.accept(visitor);

//        Map<String, SQLTableSource> aliasMap = visitor.getAliasMap();
//        aliasMap.forEach((k, v) -> {
//            System.out.println(k);
//            System.out.println(v);
//        });
        // System.out.println(outSql);

        StringBuilder outSql = new StringBuilder(32);
        //打印sql的visitor
        MySqlOutputVisitor mySqlOutputVisitor = new MySqlOutputVisitor(outSql);
        sqlStatement.accept(mySqlOutputVisitor);

        String convertedSql = outSql.toString().replace("\n", " ");
        System.out.println("转换后的sql : " + convertedSql);

        outSql = new StringBuilder(32);
        OracleOutputVisitor oracleOutputVisitor = new OracleOutputVisitor(outSql);
        sqlStatement.accept(oracleOutputVisitor);
        String cs = outSql.toString().replace("\n", " ");
        System.out.println("转换后的sql : " + cs);
    }

    /**
     * 添加条件和限制查询字段的visitor
     **/
    static class AddConditionAndControlFieldsVisitor extends MySqlASTVisitorAdapter {

        public AddConditionAndControlFieldsVisitor(Teacher teacher) {
            this.teacher = teacher;
        }

        private Teacher teacher;

        //        private Map<String, SQLTableSource> aliasMap = new HashMap<String, SQLTableSource>();
//
//        public boolean visit(SQLExprTableSource x) {
//            String alias = x.getAlias();
//            aliasMap.put(alias, x);
//            return true;
//        }


//        public boolean visit(SQLBinaryOpExpr expr) {
//            SQLExpr right = expr.getRight();
//            if(right instanceof SQLValuableExpr){
//                System.out.println(123);
//            }else {
//                System.out.println(1244);
//            }
//            return true;
//        }

        public boolean visit(SQLSelectQueryBlock expr) {
            //添加 teacherType
            //判断 如果from语句中存在这个表 添加where
            SQLTableSource studentScore = expr.getFrom().findTableSource(teacher.tableName);
            if (studentScore != null) {
                //构建condition
                SQLBinaryOpExpr sqlBinaryOpExpr = new SQLBinaryOpExpr();
                // a = b  ==>  leftExpr: a 、 rightExpr: b 、 operator: SQLBinaryOperator.Equality
                SQLPropertyExpr leftExpr = new SQLPropertyExpr();
                leftExpr.setOwner("student_score");
                leftExpr.setName(teacher.teacherTypeField);
                sqlBinaryOpExpr.setLeft(leftExpr);

                SQLIntegerExpr rightExpr = new SQLIntegerExpr();
                rightExpr.setNumber(teacher.teacherType);
                sqlBinaryOpExpr.setRight(rightExpr);

                sqlBinaryOpExpr.setOperator(SQLBinaryOperator.Equality);
                expr.addCondition(sqlBinaryOpExpr);
                expr.addCondition("sb='9'");
            }

            //限制查询字段
            List<SQLSelectItem> selectList = expr.getSelectList();
            //移除 可查询之外的字段
            selectList.removeIf(i -> !teacher.accessFields.contains(i.getExpr().toString()));
            return true;
        }

//        public Map<String, SQLTableSource> getAliasMap() {
//            return aliasMap;
//        }
    }
}
