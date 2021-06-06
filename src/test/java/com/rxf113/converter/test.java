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
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTVisitorAdapter;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试类
 *
 * @author rxf113
 */
public class test {

    @Test
    public void re() throws IOException, ClassNotFoundException {
        Teacher teacher = new Teacher();
        teacher.tableName = "rxf113";

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("C:\\Users\\11313\\Desktop\\yy.txt"));
        objectOutputStream.writeObject(teacher);
        objectOutputStream.close();

//        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("C:\\Users\\11313\\Desktop\\yy.txt"));
//        Teacher o = (Teacher) inputStream.readObject();
//        System.out.println(112);
    }

    @Test
    public void rr() throws IOException, ClassNotFoundException {



        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("C:\\Users\\11313\\Desktop\\yy.txt"));
        Teacher o = (Teacher) inputStream.readObject();
        System.out.println(112);
    }

    static class Teacher implements Serializable{
        private static long serialVersionUID = 110L;
        private int attach;
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
    public void test() {
        String sqlStr = "select name , math_score , chinese_score , eng_score from student_score";
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

        //sql输出
        StringBuilder outSql = new StringBuilder(32);
        //打印sql的visitor
        MySqlOutputVisitor mySqlOutputVisitor = new MySqlOutputVisitor(outSql);
        sqlStatement.accept(mySqlOutputVisitor);

        String convertedSql = outSql.toString().replace("\n", " ");
        System.out.println("转换后的sql : " + convertedSql);
    }

    /**
     * 添加条件 限制查询字段
     **/
    static class AddConditionAndControlFieldsVisitor extends SQLASTVisitorAdapter {

        public AddConditionAndControlFieldsVisitor(Teacher teacher) {
            this.teacher = teacher;
        }

        private Teacher teacher;

        //private addConditionHandler;

        //private fieldsControlHandler;

        public boolean visit(SQLSelectQueryBlock expr) {
            //
                //举例 name = ‘张三’ ==> leftExpr operator rightExpr
                SQLBinaryOpExpr sqlBinaryOpExpr = new SQLBinaryOpExpr();
                //name添加 teacherType
            //                //            //判断 如果from语句中存在这个表 添加where
            //                //            SQLTableSource studentScore = expr.getFrom().findTableSource(teacher.tableName);
            //                //            if (studentScore != null) {
            //                //                //构建condition
                SQLPropertyExpr leftExpr = new SQLPropertyExpr();
                leftExpr.setOwner(teacher.tableName);
                leftExpr.setName(teacher.teacherTypeField);
                sqlBinaryOpExpr.setLeft(leftExpr);
                //张三
                SQLIntegerExpr rightExpr = new SQLIntegerExpr();
                rightExpr.setNumber(teacher.teacherType);
                sqlBinaryOpExpr.setRight(rightExpr);
                //=
                sqlBinaryOpExpr.setOperator(SQLBinaryOperator.Equality);
                expr.addCondition(sqlBinaryOpExpr);

            //限制查询字段
            List<SQLSelectItem> selectList = expr.getSelectList();
            //移除 可查询之外的字段
            selectList.removeIf(i -> !teacher.accessFields.contains(i.getExpr().toString()));
            return true;
            }
    }
}