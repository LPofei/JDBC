package com.liang.bean;

import java.util.Date;

/**
 * @Description: Orader表
 * @ClassName: Orader
 * @Author: Mr.Liang
 * @Date: 2020/11/1 11:27
 * @Version: 1.0
 */
public class Order {
    private  int orderId;
    private  String  orderName;
    private  Date  orderDate;

    public Order() {
    }

    public Order(int orderId, String orderName, Date orderDate) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.orderDate = orderDate;
    }

    public int getorderId() {
        return orderId;
    }

    public void setorderId(int orderId) {
        this.orderId = orderId;
    }

    public String getorderName() {
        return orderName;
    }

    public void setorderName(String orderName) {
        this.orderName = orderName;
    }

    public Date getorderDate() {
        return orderDate;
    }

    public void setorderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Orader{" +
                "orderId=" + orderId +
                ", orderName='" + orderName + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}
