package com.liang.preparestatement;

import com.liang.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Description: 测试工具类
 * @ClassName: com.liang.preparestatement.PrepareStatementUpdateTest
 * @Author: Mr.Liang
 * @Date: 2020/10/31 19:22
 * @Version: 1.0
 */
public class PrepareStatementUpdateTest {
    
    
  /**
   * @Author Liang
   * @Description //TODO 通用的增删改操作函数验证
   * @Date 19:47 2020/10/31
   * @Param
   * @Return
   **/
    @Test
    public void test1(){
        String sql = "delete from `customers` where id = ?"; //为了防止歧义，表明用``包裹
        update(sql,19);

    }

/**
 * @Author Liang
 * @Description //写一个通用的增删改操作函数
 * @Date  
 * @Param
 * @Return eturn
 *
 * @return*/
    public int update(String sql, Object ... args){
        //1、获取数据库的连接
        Connection conn = JDBCUtils.getConnection();
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
            JDBCUtils.closeResource(conn,ps);
        }

        return 0;
    }
        
        

    @Test
    public void test(){
        //1、获取数据库的连接
        Connection conn = JDBCUtils.getConnection();

        //2、预编译sql语句，返回preparedstatement的实例
        String sql = "update customers set name = ? where id = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);

            //3、填充占位符
            ps.setString(1,"缪一凡 ");
            ps.setInt(2,19);
            //4、执行
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //5、资源的关闭
            JDBCUtils.closeResource(conn,ps);
        }

    }
    }




