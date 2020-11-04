package com.liang.bean;

import java.util.Date;

/**
 * @Description: 客户类，一个表当成一个类，类中的属性就是表的数据
 * @ClassName: Customer
 * @Author: Mr.Liang
 * @Date: 2020/10/31 20:31
 * @Version: 1.0
 */
public class Customer {
    int id;
    String name;
    String email;
    Date birth;

    public Customer() {
    }

    public Customer(int id, String name, String email, Date birth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth = birth;
    }

    public Customer(String name, String email, Date birth) {
        this.name = name;
        this.email = email;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                '}'+"\n";
    }
}
