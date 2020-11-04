package com.liang.curd;

import com.liang.bean.Customer;
import com.liang.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.*;

/**
 * @Description: 操作Blob类型的字段
 * @ClassName: BlobTest
 * @Author: Mr.Liang
 * @Date: 2020/11/1 15:33
 * @Version: 1.0
 */
public class BlobTest {

    //插入一个图片
    @Test
    public void test(){
        Connection conn = JDBCUtils.getConnection();
        String sql ="insert into customers(name,email,birth,photo) values(?,?,?,?)";
        PreparedStatement ps = null;
        FileInputStream is = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setObject(1,"张9封");
            ps.setObject(2,"423424@qq.com");
            ps.setObject(3,"1999-08-23");
            is = new FileInputStream(new File("src\\gz.jpg"));
            ps.setBlob(4,is);
            ps.execute();   //万万不要忘记提交数据
            System.out.println("插入成功");
        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            JDBCUtils.closeResource(conn,ps);
        }
    }

    //查询并获取保存数据库中的图片
    @Test
    public void test1() throws  Exception {
        Connection conn = JDBCUtils.getConnection();
        String sql = "select id ,name,email,birth,photo from customers where id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,16);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            Date birth = rs.getDate("birth");
            Customer cust = new Customer(id, name, email, birth);
            System.out.println(cust);

            Blob photo = rs.getBlob("photo");
            InputStream bs = photo.getBinaryStream();
            FileOutputStream fo = new FileOutputStream(new File("src/朱茵.jpg"));
            byte [] bytes = new  byte[1024];
            int isEmpty;
            while(( isEmpty = bs.read(bytes))!=-1){
                fo.write(bytes,0,isEmpty);
            }
            fo.close();
            bs.close();
            rs.close();
        }
        JDBCUtils.closeResource(conn,ps);


    }
}
