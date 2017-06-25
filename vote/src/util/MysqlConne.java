package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConne {
    static String driver = "com.mysql.jdbc.Driver";
    static String url = "jdbc:mysql://123.207.164.33:3306/vote?useUnicode=true&characterEncoding=utf8";
    static String user = "root";
    static String pass = "cs.swust";

    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() {
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            return conn;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void closeConn(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
