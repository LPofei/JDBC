package com.liang.connection;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * @Description: 测试与数据库链接
 * @ClassName: com.liang.connection.ConnectionTest
 * @Author: Mr.Liang
 * @Date: 2020/10/31 13:51
 * @Version: 1.0
 */
public class ConnectionTest {
    //方式一
    @Test
    public void test() throws SQLException {
        Driver driver = new com.mysql.jdbc.Driver();
        String url = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "root");
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }

    //方式二：对方式一的迭代，在如下程序中不使用第三方api，使得程序具有更好的可移植性
    @Test
    public void test1() throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        //1.获取Driver对象，使用反射
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) aClass.newInstance();
        //2.提供需要连接的数据库
        String url = "jdbc:mysql://localhost:3306/test";
        //3、提供需要连接的数据库的用户和密码
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "root");
        //4、获取连接
        Connection connect = driver.connect(url, info);
        System.out.println(connect);
    }

    //方式三：使用DriverManager来替换Driver

    @Test
    public void test2() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        //1、提供实现类的对象
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) aClass.newInstance();

        //2、提供三个基本信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "root";
        //3、注册驱动
        DriverManager.registerDriver(driver);

        //4、实现数据库链接
        Connection conn = DriverManager.getConnection(url, user,password);
        System.out.println(conn);
    }

    //方式四：对方式三的优化，可以省略一些步骤
    @Test
    public void test3() throws Exception{
        //1、提供三个基本信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "root";

        //2.加载驱动；省略一下注释的步骤，在driver类的静态代码块中自动加载
        //        Class<?> aClass =
            Class.forName("com.mysql.jdbc.Driver");
//        Driver driver = (Driver) aClass.newInstance();
//        DriverManager.registerDriver(driver);

        //3.实现数据库链接
        Connection conn = DriverManager.getConnection(url, user,password);
        System.out.println(conn);

    }

    //方式五(最终版)：通过读取配置文件的方式，实现代码和数据分离，将四个基本信息声明在配置文件当中，获取连接
    //好处：实现了代码和数据的分离，实现了解耦
    @Test
    public void test4() throws Exception {
        //1、提供四个基本信息
        Properties pro = new Properties();
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("src/jdbc.properties");
        pro.load(is);
        String url = pro.getProperty("url");
        String user = pro.getProperty("user");
        String password = pro.getProperty("password");
        String classDriver = pro.getProperty("classDriver");

        //2、加载驱动
        Class.forName(classDriver);
        //3、实现数据库链接
        Connection con = DriverManager.getConnection(url, user, password);
        System.out.println(con);
    }



}
