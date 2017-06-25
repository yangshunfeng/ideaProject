package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.User;
import util.MysqlConne;

public class UserService {
    public static List<User> selectBycondition(User user, String sort, String order, Integer page, Integer rows) {
        Connection conn = MysqlConne.getConn();
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "Select * From user where 1=1";
            if (user.id != null) sqlStr = sqlStr + " and id='" + user.id + "'";
            if (user.username != null) sqlStr = sqlStr + " and username='" + user.username + "'";
            if (user.password != null) sqlStr = sqlStr + " and password='" + user.password + "'";
            if (user.realname != null) sqlStr = sqlStr + " and realname='" + user.realname + "'";
            if (user.number != null) sqlStr = sqlStr + " and number='" + user.number + "'";
            if (user.identity != null) sqlStr = sqlStr + " and identity='" + user.identity + "'";
            if (sort != null && order != null) sqlStr = sqlStr + " order by " + sort + " " + order;
            if (page != null && rows != null) sqlStr = sqlStr + " limit " + (page - 1) * rows + "," + rows;
            ResultSet checkRs = stmt.executeQuery(sqlStr);
            List<User> userlists = User.toUsers(checkRs);
            MysqlConne.closeConn(conn);
            return userlists;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<User>();
        }
    }

    public static List<User> selectByids(String ids) {
        Connection conn = MysqlConne.getConn();
        if (ids == null || ids.equals("")) return new ArrayList<User>();
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "select * from user where id in(" + ids + ")";
            ResultSet checkRs = stmt.executeQuery(sqlStr);
            List<User> userlists = User.toUsers(checkRs);
            MysqlConne.closeConn(conn);
            return userlists;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<User>();
        }
    }

    public static Integer insert(User user) {
        Connection conn = MysqlConne.getConn();
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "insert Into user(username,password,"
                    + "realname,number,identity) Values('" + user.username + "','" +
                    user.password + "','" + user.realname + "','" + user.number + "','" +
                    user.identity + "')";
            Integer result = stmt.executeUpdate(sqlStr);
            MysqlConne.closeConn(conn);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Integer updateById(User user) {
        Connection conn = MysqlConne.getConn();
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "Update user Set" + " id='" + user.id + "'";
            if (user.username != null) sqlStr = sqlStr + ",username='" + user.username + "'";
            if (user.password != null) sqlStr = sqlStr + ",password='" + user.password + "'";
            if (user.realname != null) sqlStr = sqlStr + ",realname='" + user.realname + "'";
            if (user.number != null) sqlStr = sqlStr + ",number='" + user.number + "'";
            if (user.identity != null) sqlStr = sqlStr + ",identity='" + user.identity + "'";
            sqlStr = sqlStr + " where id=" + user.id;
            Integer result = stmt.executeUpdate(sqlStr);
            MysqlConne.closeConn(conn);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Integer deleteByIds(String ids) {
        Connection conn = MysqlConne.getConn();
        if (ids == null) return 0;
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "delete from user where id in(" + ids + ")";
            Integer result = stmt.executeUpdate(sqlStr);
            MysqlConne.closeConn(conn);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
