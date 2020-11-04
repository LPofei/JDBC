package com.liang.dao;

import com.liang.bean.Customer;
import com.liang.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLClientInfoException;
import java.util.Date;
import java.util.List;

/**
 * @Description: CustomerDAO的实现类
 * @ClassName: CustomerDAOImpl
 * @Author: Mr.Liang
 * @Date: 2020/11/1 21:13
 * @Version: 1.0
 */
public class CustomerDAOImpl extends BaseDAO implements CustomerDAO {
    @Test
    public void test() throws  Exception{

          Connection conn = JDBCUtils.getConnection();
          //测试添加数据
//        Customer cust =
//                new Customer("张费仲","321312@qq.com",new Date(new SimpleDateFormat("yyyy-MM-dd").parse("1998-02-02").getTime()));
//        insert(conn,cust);
        //测试删除一条数据
        //deleteById(conn,20);
        //返回表中最大生日
        //System.out.println(getMaxBirth(conn));
        //返回表中记录的数量
        //System.out.println(getCount(conn));
        //测试更新一条数据
        //Customer cu = new Customer(12,"张飞","3333@qq.com",new Date(3424234242L));
        //update(conn,cu);
        //测试返回所有数据
        System.out.println(getAll(conn));
        conn.close();
          //

    }
    /**
     * @param conn
     * @param customer
     * @Author Liang
     * @Description //TODO 将customer对象添加想到数据库中
     * @Date 21:06 2020/11/1
     * @Param [conn, customer]
     * @Return void
     */
    @Override
    public void insert(Connection conn, Customer customer) {
        String sql = "insert into customers(name,email,birth) values(?,?,?)";
        update(conn,sql,customer.getName(),customer.getEmail(),customer.getBirth());
    }

    /**
     * @param conn
     * @param id
     * @Author Liang
     * @Description //TODO 针对指定的ID，删除表中的一条记录
     * @Date 21:07 2020/11/1
     * @Param [conn, id]
     * @Return void
     */
    @Override
    public void deleteById(Connection conn, int id) {
        String sql="delete from customers where id=?";
        update(conn,sql,id);
    }

    /**
     * @param conn
     * @param customer
     * @Author Liang
     * @Description //TODO 针对于内存中的cust对象，去修改数据表中的指定的记录
     * @Date 21:09 2020/11/1
     * @Param [conn, customer]
     * @Return void
     */
    @Override
    public void update(Connection conn, Customer customer) {
        String sql="update customers set name=?,email=?,birth=? where id =?";
        update(conn,sql,customer.getName(),customer.getEmail(),customer.getBirth(),customer.getId());

    }

    /**
     * @param conn
     * @param id
     * @Author Liang
     * @Description //TODO 根据指定id查询得到对应的Customer对象
     * @Date 21:11 2020/11/1
     * @Param [conn, id]
     * @Return void
     * @return
     */
    @Override
    public Customer getCustomerById(Connection conn, int id) {
        String sql="select id,name,email,birth from customers where id = ?";
        Customer cust = getInstance(conn, Customer.class, sql, id);
        return  cust;
    }

    /**
     * @param conn
     * @Author Liang
     * @Description //TODO 返回表中的所有记录构成的集合
     * @Date 21:12 2020/11/1
     * @Param [conn]
     * @Return java.util.List<com.liang.bean.Customer>
     */
    @Override
    public List<Customer> getAll(Connection conn) {
        String sql = "select id,name,email,birth from customers";
        return  getForListQuery(conn,Customer.class, sql);
    }

    /**
     * @param conn
     * @Author Liang
     * @Description //TODO 返回数据表中数据的条目数
     * @Date 21:12 2020/11/1
     * @Param [conn]
     * @Return java.lang.Long
     */
    @Override
    public Long getCount(Connection conn) {
        String sql = "select COUNT(*) from customers";
        return getValue(conn,sql);
    }

    /**
     * @param conn
     * @Author Liang
     * @Description //TODO 返回数据表中最大的生日
     * @Date 21:13 2020/11/1
     * @Param [conn]
     * @Return java.util.Date
     */
    @Override
    public Date getMaxBirth(Connection conn) {
        String sql = "select MAX(birth) birth  from customers";
        return getValue(conn,sql);
    }
}
