package org.example.Util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 与数据库建立连接
 */


public class DBUtil {
    private static volatile DataSource DATASOURSE  ;
    private static String url = "jdbc:mysql://127.0.0.1:3306/musicserver?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    private static String user = "root";
    private static String password = "chen62487";


    public static Connection getConnection() {
        try{
            Connection connection = getDatasourse().getConnection();
            return connection;
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("连接失败");

        }

    }


    //双重if判断是否已经建立连接
    private static DataSource getDatasourse() {
        if (DATASOURSE == null) {
            synchronized (DBUtil.class){
                if (DATASOURSE==null){
                    DATASOURSE = new MysqlDataSource();
                    ((MysqlDataSource) DATASOURSE).setURL(url);
                    ((MysqlDataSource) DATASOURSE).setUser(user);
                    ((MysqlDataSource) DATASOURSE).setPassword(password);
                }
            }
        }
        return DATASOURSE;
    }


    public static void getclose(Connection connection, PreparedStatement ps, ResultSet rs) {

        if (rs != null) {
            try{
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
