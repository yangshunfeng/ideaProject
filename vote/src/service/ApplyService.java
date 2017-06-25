package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Apply;
import util.MysqlConne;

public class ApplyService {
    public static List<Apply> selectBycondition(Apply apply, String sort, String order, Integer page, Integer rows) {
        Connection conn = MysqlConne.getConn();
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "Select * From apply where 1=1";
            if (apply.id != null) sqlStr = sqlStr + " and id='" + apply.id + "'";
            if (apply.vote_id != null) sqlStr = sqlStr + " and vote_id='" + apply.vote_id + "'";
            if (apply.user_id != null) sqlStr = sqlStr + " and user_id='" + apply.user_id + "'";
            if (apply.introduction != null) sqlStr = sqlStr + " and introduction='" + apply.introduction + "'";
            if (apply.is_accept != null) sqlStr = sqlStr + " and is_accept='" + apply.is_accept + "'";
            if (sort != null && order != null) sqlStr = sqlStr + " order by " + sort + " " + order;
            if (page != null && rows != null) sqlStr = sqlStr + " limit " + (page - 1) * rows + "," + rows;
            ResultSet checkRs = stmt.executeQuery(sqlStr);
            List<Apply> applylists = Apply.toApplys(checkRs);
            MysqlConne.closeConn(conn);
            return applylists;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Apply>();
        }
    }

    public static Integer insert(Apply apply) {
        Connection conn = MysqlConne.getConn();
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "insert Into apply(vote_id,user_id,"
                    + "introduction,is_accept) Values('" + apply.vote_id + "','" +
                    apply.user_id + "','" + apply.introduction + "','" + apply.is_accept + "')";
            Integer result = stmt.executeUpdate(sqlStr);
            MysqlConne.closeConn(conn);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Integer updateById(Apply apply) {
        Connection conn = MysqlConne.getConn();
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "Update apply Set" + " id='" + apply.id + "'";
            if (apply.vote_id != null) sqlStr = sqlStr + ",vote_id='" + apply.vote_id + "'";
            if (apply.user_id != null) sqlStr = sqlStr + ",user_id='" + apply.user_id + "'";
            if (apply.introduction != null) sqlStr = sqlStr + ",introduction='" + apply.introduction + "'";
            if (apply.is_accept != null) sqlStr = sqlStr + ",is_accept='" + apply.is_accept + "'";
            sqlStr = sqlStr + " where id=" + apply.id;
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
            String sqlStr = "delete from apply where id in(" + ids + ")";
            Integer result = stmt.executeUpdate(sqlStr);
            MysqlConne.closeConn(conn);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Integer deleteBycondition(Apply apply) {
        Connection conn = MysqlConne.getConn();
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "delete from apply where 1=1";
            if (apply.id != null) sqlStr = sqlStr + " and id='" + apply.id + "'";
            if (apply.vote_id != null) sqlStr = sqlStr + " and vote_id='" + apply.vote_id + "'";
            if (apply.user_id != null) sqlStr = sqlStr + " and user_id='" + apply.user_id + "'";
            if (apply.introduction != null) sqlStr = sqlStr + " and introduction='" + apply.introduction + "'";
            if (apply.is_accept != null) sqlStr = sqlStr + " and is_accept='" + apply.is_accept + "'";
            Integer result = stmt.executeUpdate(sqlStr);
            MysqlConne.closeConn(conn);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
