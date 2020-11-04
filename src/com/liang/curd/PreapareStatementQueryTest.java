package com.liang.curd;

import com.liang.bean.Order;
import com.liang.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 针对不同表的查询操作写一个通用的方法
 * @ClassName: PreapareStatementQueryTest
 * @Author: Mr.Liang
 * @Date: 2020/11/1 11:26
 * @Version: 1.0
 */
public class PreapareStatementQueryTest {
    //测试如下的函数
    //避免引起歧义，order用``包裹
    //尽量用getColumnLable（）来获取字段名，可以获取别名
    @Test
    public void test(){

        String sql="select order_Id orderId ,order_Name orderName ,order_Date orderDate from `order` where order_Id<? ";
        List<Order> orderList = getForListQuery(Order.class, sql, 5);
        orderList.forEach(System.out::println);

    }


    /**
     * @Author Liang
     * @Description //TODO 查询通用表的操作返回多条数据
     * @Date 11:49 2020/11/1
     * @Param [clazz, sql, args]
     * @Return java.util.List<T>
     **/
    public <T> List<T> getForListQuery( Class<T> clazz,String sql, Object ... args){

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //1.获取数据库连接
            conn = JDBCUtils.getConnection();
            //2.返回statement对象
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {    //给？赋值
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();  //提交查询语句并返回查询到的结果集
            ResultSetMetaData md = rs.getMetaData(); //获取结果集的元数据 。元数据：修饰数据的数据，就是字段，字段修饰的就是数据
            int columnCount = md.getColumnCount();   //获取字段的列数
            ArrayList<T> list = new ArrayList<>();
            while(rs.next()){
                //Customer cust =  new Customer();
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = md.getColumnLabel(i + 1);   //循环获取每个字段的字段名给value
                    Object value = rs.getObject(i+1);
                    //现 在我们得到了字段名，面对查询语句中可能select n个字段，使用反射动态的给每个customer的属性赋值，字段名就对应着customer类的属性
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);  //这一步很重要，获取修改访问权限
                    field.set(t,value);
                }
                //System.out.println(cust);
                list.add(t);
            }
            return list;

        } catch (SQLException | NoSuchFieldException | IllegalAccessException | InstantiationException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }
        return null;
    }





    }


