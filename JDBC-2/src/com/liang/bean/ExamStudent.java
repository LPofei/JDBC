package com.liang.bean;

/**
 * @Description: 学生考试信息表
 * @ClassName: examstudent
 * @Author: Mr.Liang
 * @Date: 2020/11/1 14:47
 * @Version: 1.0
 */
public class ExamStudent {
    private  int FlowID;
    private  int Type;
    private  String  IDCard;
    private  String  ExamCard;
    private  String  StudentName;
    private  String  Location;
    private  int Grade;

    public ExamStudent() {
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getExamCard() {
        return ExamCard;
    }

    public void setExamCard(String examCard) {
        ExamCard = examCard;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getGrade() {
        return Grade;
    }

    public void setGrade(int grade) {
        Grade = grade;
    }

    public int getFlowID() {
        return FlowID;
    }

    public void setFlowID(int flowID) {
        FlowID = flowID;
    }

    @Override
    public String toString() {
        return "流水号：" + FlowID +"\n"+
                "四六级：" + Type + "\n" +
                "身份证号：" + IDCard + "\n" +
                "准考证号：" + ExamCard + "\n" +
                "学生姓名：" + StudentName + "\n" +
                "区域：" + Location + "\n"+
                "成绩：" + Grade + "\n" ;
    }
}
