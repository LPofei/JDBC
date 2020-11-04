package com.liang2.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sun.org.glassfish.gmbal.Description;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import javax.xml.transform.Source;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @Description: 使用c3p0的数据库连接池技术
 * @ClassName: JDBCUtils
 * @Author: Mr.Liang
 * @Date: 2020/11/2 14:14
 * @Version: 1.0
 */
public class JDBCUtils {


    @Test
    public void test() throws SQLException {
        QueryRunner qr = new QueryRunner();
        Connection conn = JDBCUtils.getConnectionDruid();
        String sql ="insert into customers(name,email,birth) values(?,?,?)";
        qr.update(conn,sql,"缪么发","34234@324.com","1998-11-11");
    }
    /**
     * @Author Liang
     * @Description //使用c3p0的数据库连接池技术获取一个连接
     * @Date
     * @Param
     * @return
             **/
    //数据库连接池只需要一个即可
    private  static  ComboPooledDataSource cpds = new ComboPooledDataSource("hellocp30");

    public static Connection getConnection() throws SQLException {
         Connection conn = cpds.getConnection();
         return conn;
    }

    private static DataSource source;
    static {
         Properties pro = new Properties();
         InputStream rs = ClassLoader.getSystemClassLoader().getResourceAsStream("Druid.properties");
        try {
            pro.load(rs);
            source = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @Author Liang
     * @Description //TODO 使用druid数据库连接池技术获取一个连接
     * @Date 15:03 2020/11/2
     * @Param []
     * @Return java.sql.Connection
     **/
    public static Connection getConnectionDruid() throws SQLException {
        Connection conn = source.getConnection();
        return conn;
    }


}
