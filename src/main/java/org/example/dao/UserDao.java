package org.example.dao;


import org.example.Util.DBUtil;
import org.example.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现用户登录
 */

public class UserDao {
    public  User login(User loginUser){
        User user = null;
        Connection connection =  null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "select * from user where username=? and password=?";
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1,loginUser.getUsername());
            ps.setString(2,loginUser.getPassword());

            rs = ps.executeQuery();

            while (rs.next()) {

               user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("Gender"));
                user.setEmail(rs.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return user;
    }


    public static void main(String[] args) {
//        User user = new User();
//        user.setUsername("珍妮");
//        user.setPassword("123");
//        User user1 = login(user);
//        System.out.println(user1);

    }

}
