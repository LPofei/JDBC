package com.liang.dao;

import com.liang.bean.Customer;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * @Description: 此接口用于规范针对于customers表的常用操作
 * @ClassName: CustomerDao
 * @Author: Mr.Liang
 * @Date: 2020/11/1 21:05
 * @Version: 1.0
 */
public interface CustomerDAO {
    /**
     * @Author Liang
     * @Description //TODO 将customer对象添加想到数据库中
     * @Date 21:06 2020/11/1
     * @Param [conn, customer]
     * @Return void
     **/
    void insert(Connection conn, Customer customer);

    /**
     * @Author Liang
     * @Description //TODO 针对指定的ID，删除表中的一条记录
     * @Date 21:07 2020/11/1
     * @Param [conn, id]
     * @Return void
     **/
    void deleteById(Connection conn,int id );

    /**
     * @Author Liang
     * @Description //TODO 针对于内存中的cust对象，去修改数据表中的指定的记录
     * @Date 21:09 2020/11/1
     * @Param [conn, customer]
     * @Return void
     **/
    void update(Connection conn,Customer customer);


    /**
     * @Author Liang
     * @Description //TODO 根据指定id查询得到对应的Customer对象
     * @Date 21:11 2020/11/1
     * @Param [conn, id]
     * @Return void
     *
     * @return*/
    Customer getCustomerById(Connection conn, int id);


    /**
     * @Author Liang
     * @Description //TODO 返回表中的所有记录构成的集合
     * @Date 21:12 2020/11/1
     * @Param [conn]
     * @Return java.util.List<com.liang.bean.Customer>
     **/
    List<Customer> getAll(Connection conn);
    
    /**
     * @Author Liang
     * @Description //TODO 返回数据表中数据的条目数
     * @Date 21:12 2020/11/1
     * @Param [conn]
     * @Return java.lang.Long
     **/
    Long getCount(Connection conn);

    /**
     * @Author Liang
     * @Description //TODO 返回数据表中最大的生日
     * @Date 13:52 2020/11/2
     * @Param [conn]
     * @Return java.util.Date
     **/
    Date getMaxBirth(Connection conn);

}
