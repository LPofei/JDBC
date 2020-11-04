package com.liang.curd;

import com.liang.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Description: 批量操作测试
 * @ClassName: InsertTest
 * @Author: Mr.Liang
 * @Date: 2020/11/1 17:00
 * @Version: 1.0
 */

//向goods表中插入20000条数据
public class InsertTest {

    //批量插入效率最高的方式：
    @Test
    public void test(){
        long start = System.currentTimeMillis();
        Connection conn = JDBCUtils.getConnection();
        PreparedStatement ps = null;
        try {
            conn.setAutoCommit(false);  //设置不允许自动提交数据，操作完后一起提交，提高数据库效率
            String sql = "insert into goods(name) values(?)";
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < 2000000; i++) {
                ps.setString(1,"name_"+i);

                //1.攒 sql  //不一条一条的执行，而是攒够一波一起执行
                ps.addBatch();

                if((i%50)==0){
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps);
        }
        long end = System.currentTimeMillis();
        System.out.println(start-end);

    }

}
