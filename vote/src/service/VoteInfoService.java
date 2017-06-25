package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.VoteInfo;
import util.BaseServlet;
import util.MysqlConne;

@SuppressWarnings("serial")
public class VoteInfoService extends BaseServlet {
    public static List<VoteInfo> selectBycondition(VoteInfo voteInfo, String sort, String order, Integer page, Integer rows) {
        Connection conn = MysqlConne.getConn();
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "Select * From voteinfo where 1=1";
            if (voteInfo.id != null) sqlStr = sqlStr + " and id='" + voteInfo.id + "'";
            if (voteInfo.title != null) sqlStr = sqlStr + " and title='" + voteInfo.title + "'";
            if (voteInfo.description != null) sqlStr = sqlStr + " and description='" + voteInfo.description + "'";
            if (voteInfo.is_anonymous != null) sqlStr = sqlStr + " and is_anonymous='" + voteInfo.is_anonymous + "'";
            if (voteInfo.state != null) sqlStr = sqlStr + " and state='" + voteInfo.state + "'";
            if (sort != null && order != null) sqlStr = sqlStr + " order by " + sort + " " + order;
            if (page != null && rows != null) sqlStr = sqlStr + " limit " + (page - 1) * rows + "," + rows;
            ResultSet checkRs = stmt.executeQuery(sqlStr);
            List<VoteInfo> voteInfolists = VoteInfo.toVoteInfos(checkRs);
            MysqlConne.closeConn(conn);
            return voteInfolists;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<VoteInfo>();
        }
    }

    public static Integer insert(VoteInfo voteInfo) {
        Connection conn = MysqlConne.getConn();
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "insert Into voteinfo(title,description,"
                    + "is_anonymous,state) Values('" + voteInfo.title + "','" +
                    voteInfo.description + "','" + voteInfo.is_anonymous + "','" + voteInfo.state + "')";
            Integer result = stmt.executeUpdate(sqlStr);
            MysqlConne.closeConn(conn);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Integer updateById(VoteInfo voteInfo) {
        Connection conn = MysqlConne.getConn();
        try {
            Statement stmt = conn.createStatement();
            String sqlStr = "Update voteinfo Set" + " id='" + voteInfo.id + "'";
            if (voteInfo.title != null) sqlStr = sqlStr + ",title='" + voteInfo.title + "'";
            if (voteInfo.description != null) sqlStr = sqlStr + ",description='" + voteInfo.description + "'";
            if (voteInfo.is_anonymous != null) sqlStr = sqlStr + ",is_anonymous='" + voteInfo.is_anonymous + "'";
            if (voteInfo.state != null) sqlStr = sqlStr + ",state='" + voteInfo.state + "'";
            sqlStr = sqlStr + " where id=" + voteInfo.id;
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
            String sqlStr = "delete from voteinfo where id in(" + ids + ")";
            Integer result = stmt.executeUpdate(sqlStr);
            MysqlConne.closeConn(conn);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
