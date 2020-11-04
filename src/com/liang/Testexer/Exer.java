package com.liang.Testexer;

import com.liang.bean.ExamStudent;
import com.liang.curd.PreapareStatementQueryTest;
import com.liang.preparestatement.PrepareStatementUpdateTest;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @Description: 练习
 * @ClassName: Exer
 * @Author: Mr.Liang
 * @Date: 2020/11/1 12:52
 * @Version: 1.0
 */
public class Exer {
    /**
     * @Author Liang
     * @Description //TODO 练习一，向customer表中插入一条数据
     * @Date 13:39 2020/11/1
     * @Param []
     * @Return void
     **/
    @Test
    public void test(){
        PrepareStatementUpdateTest psu = new PrepareStatementUpdateTest();
        Scanner sc = new Scanner(System.in);


        System.out.println("请输入要插入的名字：");
        String name = sc.next();
        System.out.println("请输入要插入的邮箱：");
        String email = sc.next();
        System.out.println("请输入要插入的生日:(格式yyyy-MM-dd)");
        String date = sc.next();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = null;
        try {
            birth = sd.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String sql = "insert into customers(name,email,birth) values (?,?,?)";
        int inserCount = psu.update(sql, name, email, new Date(birth.getTime()));
        if (inserCount>0){
            System.out.println("插入成功！");
        }else
        {
            System.out.println("插入失败");
        }

    }
    /**
     * @Author Liang
     * @Description //TODO 向examstudent表中插入考生信息
     * @Date 13:40 2020/11/1
     * @Param []
     * @Return void
     **/
    @Test
    public void test1(){
        PrepareStatementUpdateTest psu = new PrepareStatementUpdateTest();
        Scanner sc = new Scanner(System.in);

        System.out.println("请输入要录入的信息");
        System.out.println("Type：");
        int type = sc.nextInt();
        System.out.println("IDCard：");
        String idcard = sc.next();
        System.out.println("ExamCard:");
        String examcard = sc.next();
        System.out.println("StudentName:");
        String studentname = sc.next();
        System.out.println("Location:");
        String location = sc.next();
        System.out.println("Grade:");
        String grade = sc.next();

        String sql = "insert into examstudent(Type ,IDCard,ExamCard,Studentname,Location,Grade) values (?,?,?,?,?,?)";
        int inserCount = psu.update(sql, type, idcard, examcard,studentname,location,grade);
        if (inserCount>0){
            System.out.println("录入成功！");
        }else
        {
            System.out.println("录入失败");
        }
    }

    /**
     * @Author Liang
     * @Description //TODO 输入身份证号或者准考证号可以查询到学生的基本信息
     * @Date 13:51 2020/11/1
     * @Param []
     * @Return void
     **/
    @Test
    public void test2(){
        while(true){
            Scanner sc = new Scanner(System.in);
            System.out.println("请选择您要输入的类型：");
            System.out.println("a：准考证号");
            System.out.println("b：身份证号");
            String select = sc.next();
            if("a".equals(select)){
                queryExamNumber();
            }else if ("b".equals(select)){
                queryIdNumber();
            }else{
                System.out.println("您的输入有误，请重新进入程序");
            }
        }
    }

    /**
     * @Author Liang
     * @Description //TODO 完成学生信息的删除功能
     * @Date 15:15 2020/11/1
     * @Param []
     * @Return void
     **/
    @Test
    public void test4(){

        PrepareStatementUpdateTest psu = new PrepareStatementUpdateTest();
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要删除的学生的考号：");
        String examcard = sc.next();
        String sql= "delete from examstudent where ExamCard=?";
        int count = psu.update(sql, examcard);
        if(count>0){
            System.out.println("删除成功！");
        }else {
            System.out.println("查无此人，请重新输入！");
        }

    }

    private void queryExamNumber() {
        PreapareStatementQueryTest psq = new PreapareStatementQueryTest();
        System.out.println("请输入准考证号");
        Scanner input = new Scanner(System.in);
        String examid = input.next();
        String sql="select * from examstudent where ExamCard=?";
        List<ExamStudent> examStudentList = psq.getForListQuery(ExamStudent.class, sql, examid);
        if(examStudentList.size()>0){
            System.out.println("=======查询结果======");
            for (int i = 0; i < examStudentList.size(); i++) {
                System.out.println(examStudentList.get(i));
            }
        }else {
            System.out.println("查无此人！请重新进入程序");
        }


    }

    private void queryIdNumber() {
        PreapareStatementQueryTest psq = new PreapareStatementQueryTest();
        System.out.println("请输入身份证号");
        Scanner input = new Scanner(System.in);
        String id = input.next();
        String sql="select * from examstudent where IDCard=?";
        List<ExamStudent> examStudentList = psq.getForListQuery(ExamStudent.class, sql, id);
        if(examStudentList.size()>0){
            System.out.println("=======查询结果======");
            for (int i = 0; i < examStudentList.size(); i++) {
                System.out.println(examStudentList.get(i));
            }
        }else {
            System.out.println("查无此人！请重新进入程序");
        }

    }


}
