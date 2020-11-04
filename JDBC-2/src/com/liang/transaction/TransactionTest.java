package com.liang.transaction;

import com.liang.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Description: 数据库事务模拟
 * @ClassName: TransactionTest
 * @Author: Mr.Liang
 * @Date: 2020/11/1 19:10
 * @Version: 1.0
 */
public class TransactionTest {

    //数据库事务的实现：数据库中的 一个操作被终止时，应该回滚当操作之前的状态，所以所有的操作都在最后提交
    @Test
    public void test(){
        Connection conn = null;
        try {
            conn=JDBCUtils.getConnection();
            conn.setAutoCommit(false);  //取消数据的自动提交
            String sql= "update user_table set balance = balance-1000 where user=?";
            update(conn,sql, "AA");

            System.out.println(10/0);//模拟网络异常

            String sql1= "update user_table set balance = balance+1000 where user=?";
            update(conn,sql1, "BB");
            System.out.println("转账成功");
            conn.commit();
        } catch (Exception e) {
            try {
                //出现异常，回滚数据
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }






    public int update(Connection conn, String sql, Object ... args)  {

        //2、预编译sql语句，返回preparedstatement的实例
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            //3、填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            //4、执行
            return ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //5、资源的关闭
            JDBCUtils.closeResource(null,ps);
        }
        return 0;
    }
}
