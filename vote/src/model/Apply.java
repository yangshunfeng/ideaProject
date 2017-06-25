package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Apply {
    public Integer id;
    public Integer vote_id;
    public Integer user_id;
    public String introduction;
    public Integer is_accept;

    public Apply() {
    }

    public Apply(HttpServletRequest request) {
        if (request.getParameter("id") != null && request.getParameter("id").equals("") != true)
            this.id = Integer.parseInt(request.getParameter("id"));
        if (request.getParameter("vote_id") != null && request.getParameter("vote_id").equals("") != true)
            this.vote_id = Integer.parseInt(request.getParameter("vote_id"));
        if (request.getParameter("user_id") != null && request.getParameter("user_id").equals("") != true)
            this.user_id = Integer.parseInt(request.getParameter("user_id"));
        if (request.getParameter("introduction") != null && request.getParameter("introduction").equals("") != true)
            this.introduction = request.getParameter("introduction");
        if (request.getParameter("is_accept") != null && request.getParameter("is_accept").equals("") != true)
            this.is_accept = Integer.parseInt(request.getParameter("is_accept"));
    }

    public static List<Apply> toApplys(ResultSet resultSet) {
        List<Apply> list = new ArrayList<Apply>();
        try {
            while (resultSet.next()) {
                Apply apply = new Apply();
                apply.id = resultSet.getInt("id");
                apply.vote_id = resultSet.getInt("vote_id");
                apply.user_id = resultSet.getInt("user_id");
                apply.introduction = resultSet.getString("introduction");
                apply.is_accept = resultSet.getInt("is_accept");
                list.add(apply);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
