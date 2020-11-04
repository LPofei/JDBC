package com.liang.preparestatement;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * @Description: 测试操作数据库
 * @ClassName: com.liang.preparestatement.PrepareStatementTest
 * @Author: Mr.Liang
 * @Date: 2020/10/31 16:16
 * @Version: 1.0
 */
public class PrepareStatementTest {

    @Test
    public void test(){
        //1、提供四个基本信息
        Properties pro = new Properties();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("src/jdbc.properties");
        Connection con = null;
        PreparedStatement ps = null;
        try {
            pro.load(is);
            String url = pro.getProperty("url");
            String user = pro.getProperty("user");
            String password = pro.getProperty("password");
            String classDriver = pro.getProperty("classDriver");

            //2、加载驱动
            Class.forName(classDriver);
            //3、实现数据库链接
            con = DriverManager.getConnection(url, user, password);

            String sql="insert into customers(name,email,birth) values (?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1,"梁鹏飞");
            ps.setString(2,"1832912300@qq.com");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse("1999-06-13");
            ps.setDate(3,  new java.sql.Date(date.getTime()));
            ps.execute();

        } catch (IOException | ClassNotFoundException | SQLException |ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps!=null)
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if(con!=null)
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }



    }

}
