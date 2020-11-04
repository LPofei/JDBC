package com.liang.CP30;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Description: cp30数据库连接池驱动测试
 * @ClassName: CP30Test
 * @Author: Mr.Liang
 * @Date: 2020/11/2 13:53
 * @Version: 1.0
 */
public class CP30Test {
    //测试数据库连接池能否正常使用
    @Test
    public void test(){
        ComboPooledDataSource cpds = new ComboPooledDataSource("hellocp30");
        Connection conn = null;
        try {
            conn = cpds.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(conn);

    }

}
