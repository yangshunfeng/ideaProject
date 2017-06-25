package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class VoteInfo {
    public Integer id;
    public String title;
    public String description;
    public Integer is_anonymous;
    public Integer state;

    public VoteInfo() {
    }

    ;

    public VoteInfo(HttpServletRequest request) {
        if (request.getParameter("id") != null && request.getParameter("id").equals("") != true) {
            this.id = Integer.parseInt(request.getParameter("id"));
        }
        if (request.getParameter("title") != null && request.getParameter("title").equals("") != true) {
            this.title = request.getParameter("title");
        }
        if (request.getParameter("description") != null && request.getParameter("description").equals("") != true) {
            this.description = request.getParameter("description");
        }
        if (request.getParameter("is_anonymous") != null && request.getParameter("is_anonymous").equals("") != true) {
            this.is_anonymous = Integer.parseInt(request.getParameter("is_anonymous"));
        }
        if (request.getParameter("state") != null && request.getParameter("state").equals("") != true) {
            this.state = Integer.parseInt(request.getParameter("state"));
        }
    }

    public static List<VoteInfo> toVoteInfos(ResultSet resultSet) {
        List<VoteInfo> list = new ArrayList<VoteInfo>();
        try {
            while (resultSet.next()) {
                VoteInfo voteInfo = new VoteInfo();
                voteInfo.id = resultSet.getInt("id");
                voteInfo.title = resultSet.getString("title");
                voteInfo.description = resultSet.getString("description");
                voteInfo.is_anonymous = resultSet.getInt("is_anonymous");
                voteInfo.state = resultSet.getInt("state");
                list.add(voteInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}