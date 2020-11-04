package com.liang.curd;

import com.liang.bean.Customer;
import com.liang.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @Description: 针对于customers表的通用查询操作
 * @ClassName: CustomerForQuery
 * @Author: Mr.Liang
 * @Date: 2020/10/31 20:36
 * @Version: 1.0
 */

/**
 针对于表的字段名与L类的属性名不同的情况：
 1. 必须声明sql时，使用累的属性名来命名字段的别名
 2.使用resultsetmetadata时，需要使用getColumnLabel（）来替换getColumnName（）获取列的别名
 说明：如果sql中没有给字段其别名，getClolumnLabel()获取的就是别名
 **/
public class CustomerForQuery {
    //测试下面的查询函数
    @Test
    public void test(){
        String sql = "select id ,name from customers where id = ?";
        Customer cust = queryForCustomers(sql, 10);
        System.out.println(cust);

    }

    
    
    /**
     * @Author Liang
     * @Description //TODO 针对Customer的查询操作
     * @Date 11:28 2020/11/1
     * @Param [sql, args]
     * @Return com.liang.bean.Customer
     **/
    public Customer queryForCustomers(String sql, Object ... args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //1.获取数据库连接
             conn = JDBCUtils.getConnection();
            //2.返回statement对象
             ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
             rs = ps.executeQuery();  //提交查询语句并返回查询到的结果集
            if(rs.next()){
                ResultSetMetaData md = rs.getMetaData(); //获取结果集的元数据 。元数据：修饰数据的数据，就是字段，字段修饰的就是数据
                int columnCount = md.getColumnCount();
                Customer cust =  new Customer();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = md.getColumnName(i + 1);   //循环获取每个字段的字段名给value
                    Object value = rs.getObject(i+1);
                    //现 在我们得到了字段名，面对查询语句中可能select n个字段，使用反射动态的给每个customer的属性赋值，字段名就对应着customer类的属性
                    Field field = Customer.class.getDeclaredField(columnName);
                    field.setAccessible(true);  //这一步很重要，获取修改访问权限
                    field.set(cust,value);
                }
                //System.out.println(cust);
                return cust;
            }
        } catch (SQLException | NoSuchFieldException | IllegalAccessException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }
        return null;
    }
}
