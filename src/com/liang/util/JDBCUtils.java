package com.liang.util;

import com.liang.connection.ConnectionTest;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @Description: 数据库链接工具类
 * @ClassName: com.liang.util.JDBCUtils
 * @Author: Mr.Liang
 * @Date: 2020/10/31 18:57
 * @Version: 1.0
 */
public class JDBCUtils {

    /**
     * @Author Liang
     * @Description //获取一个数据库连接
     * @Date
     * @Param
     * @return
     **/
       public static Connection getConnection(){
           Connection conn=null;
           //1、提供四个基本信息
           try {
               Properties pro = new Properties();
               InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
               pro.load(is);
               String url = pro.getProperty("url");
               String user = pro.getProperty("user");
               String password = pro.getProperty("password");
               String classDriver = pro.getProperty("classDriver");

               //2、加载驱动
               Class.forName(classDriver);
               //3、实现数据库链接
               conn = DriverManager.getConnection(url, user, password);

           } catch (IOException | SQLException | ClassNotFoundException e) {
               e.printStackTrace();
           }finally {
               if(conn!=null){
                   return conn;
               }
           }
           return null;
       }


    /**
     * @Author Liang
     * @Description //TODO 关闭数据库连接和Statement
     * @Date 19:53 2020/10/31
     * @Param 
     * @Return 
     **/
       public static void closeResource(Connection connection, Statement statement){
           try {
               if(statement!=null)
                   statement.close();
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }
           try {
               if(statement!=null)
                   statement.close();
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }

       }

    public static void closeResource(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(statement!=null)
                statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if(statement!=null)
                statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
