package com.liang.dao;

import com.liang.util.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 封装了针对于数据表的通用操作
 * @ClassName: BaseDao
 * @Author: Mr.Liang
 * @Date: 2020/11/1 20:38
 * @Version: 2.0
 */
public abstract class BaseDAO {
    /**
     * @Author Liang
     * @Description //TODO 通用的增删改操作-----考虑了事务
     * @Date 20:39 2020/11/1
     * @Param [conn, sql, args]
     * @Return int
     **/
    public int update(Connection conn, String sql, Object... args) {

        //预编译sql语句，返回preparedstatement的实例
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //执行
            return ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //5、资源的关闭
            JDBCUtils.closeResource(null, ps);
        }
        return 0;
    }

    /**
     * @Author Liang
     * @Description //TODO 针对于数据库的查询操作，返回一条记录
     * @Date 20:50 2020/11/1
     * @Param [conn, clazz, sql, args]
     * @Return T
     **/
    public <T> T getInstance(Connection conn, Class<T> clazz, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //1.获取数据库连接
            conn = JDBCUtils.getConnection();
            //2.返回statement对象
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {    //给？赋值
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();  //提交查询语句并返回查询到的结果集
            ResultSetMetaData md = rs.getMetaData(); //获取结果集的元数据 。元数据：修饰数据的数据，就是字段，字段修饰的就是数据
            int columnCount = md.getColumnCount();   //获取字段的列数
            if (rs.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = md.getColumnLabel(i + 1);   //循环获取每个字段的字段名给value
                    Object value = rs.getObject(i + 1);
                    //现 在我们得到了字段名，面对查询语句中可能select n个字段，使用反射动态的给每个customer的属性赋值，字段名就对应着customer类的属性
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);  //这一步很重要，获取修改访问权限
                    field.set(t, value);
                }
                return t;
            }
        } catch (SQLException | NoSuchFieldException | IllegalAccessException | InstantiationException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps, rs);
        }
        return null;
    }

    /**
     * @Author Liang
     * @Description //TODO 通用表的查询操作，返回多条记录
     * @Date 20:43 2020/11/1
     * @Param [clazz, sql, args]
     * @Return java.util.List<T>
     **/

    public <T> List<T> getForListQuery(Connection conn, Class<T> clazz, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //1.获取数据库连接
            conn = JDBCUtils.getConnection();
            //2.返回statement对象
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {    //给？赋值
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();  //提交查询语句并返回查询到的结果集
            ResultSetMetaData md = rs.getMetaData(); //获取结果集的元数据 。元数据：修饰数据的数据，就是字段，字段修饰的就是数据
            int columnCount = md.getColumnCount();   //获取字段的列数
            ArrayList<T> list = new ArrayList<>();
            while (rs.next()) {
                //Customer cust =  new Customer();
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = md.getColumnLabel(i + 1);   //循环获取每个字段的字段名给value
                    Object value = rs.getObject(i + 1);
                    //现 在我们得到了字段名，面对查询语句中可能select n个字段，使用反射动态的给每个customer的属性赋值，字段名就对应着customer类的属性
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);  //这一步很重要，获取修改访问权限
                    field.set(t, value);
                }
                list.add(t);
            }
            return list;

        } catch (SQLException | NoSuchFieldException | IllegalAccessException | InstantiationException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps, rs);
        }
        return null;
    }

    public <T> T getValue(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                return (T) rs.getObject(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps, rs);
        }
        return null;
    }

}

