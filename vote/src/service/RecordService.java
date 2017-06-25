package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Record;
import util.MysqlConne;

public class RecordService {
    public static List<Record> selectBycondition(Record record, String sort, String order, Integer page, Integer rows) {
        Connection conn = MysqlConne.getConn();
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "Select * From record where 1=1";
            if (record.id != null) sqlStr = sqlStr + " and id='" + record.id + "'";
            if (record.vote_id != null) sqlStr = sqlStr + " and vote_id='" + record.vote_id + "'";
            if (record.send_id != null) sqlStr = sqlStr + " and send_id='" + record.send_id + "'";
            if (record.aim_id != null) sqlStr = sqlStr + " and aim_id='" + record.aim_id + "'";
            if (record.introduction != null) sqlStr = sqlStr + " and introduction='" + record.introduction + "'";
            if (sort != null && order != null) sqlStr = sqlStr + " order by " + sort + " " + order;
            if (page != null && rows != null) sqlStr = sqlStr + " limit " + (page - 1) * rows + "," + rows;
            ResultSet checkRs = stmt.executeQuery(sqlStr);
            List<Record> recordlists = Record.toRecords(checkRs);
            MysqlConne.closeConn(conn);
            return recordlists;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Record>();
        }
    }

    public static Integer insert(Record record) {
        Connection conn = MysqlConne.getConn();
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "insert Into record(vote_id,send_id,"
                    + "aim_id,introduction) Values('" + record.vote_id + "','" +
                    record.send_id + "','" + record.aim_id + "','" + record.introduction + "')";
            Integer result = stmt.executeUpdate(sqlStr);
            MysqlConne.closeConn(conn);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Integer updateById(Record record) {
        Connection conn = MysqlConne.getConn();
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "Update record Set" + " id='" + record.id + "'";
            if (record.vote_id != null) sqlStr = sqlStr + ",vote_id='" + record.vote_id + "'";
            if (record.send_id != null) sqlStr = sqlStr + ",send_id='" + record.send_id + "'";
            if (record.aim_id != null) sqlStr = sqlStr + ",aim_id='" + record.aim_id + "'";
            if (record.introduction != null) sqlStr = sqlStr + ",introduction='" + record.introduction + "'";
            sqlStr = sqlStr + " where id=" + record.id;
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
        if (ids == null || ids.equals("")) return 0;
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "delete from record where id in(" + ids + ")";
            Integer result = stmt.executeUpdate(sqlStr);
            MysqlConne.closeConn(conn);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Integer deleteBycondition(Record record) {
        Connection conn = MysqlConne.getConn();
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "delete from record where 1=1";
            if (record.id != null) sqlStr = sqlStr + " and id='" + record.id + "'";
            if (record.vote_id != null) sqlStr = sqlStr + " and vote_id='" + record.vote_id + "'";
            if (record.send_id != null) sqlStr = sqlStr + " and send_id='" + record.send_id + "'";
            if (record.aim_id != null) sqlStr = sqlStr + " and aim_id='" + record.aim_id + "'";
            if (record.introduction != null) sqlStr = sqlStr + " and introduction='" + record.introduction + "'";
            Integer result = stmt.executeUpdate(sqlStr);
            MysqlConne.closeConn(conn);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
