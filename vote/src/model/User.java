package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class User {
    public Integer id;
    public String username;
    public String password;
    public String realname;
    public String number;
    public Integer identity;

    public User() {
    }

    public User(HttpServletRequest request) {
        if (request.getParameter("id") != null && request.getParameter("id").equals("") != true)
            this.id = Integer.parseInt(request.getParameter("id"));
        if (request.getParameter("username") != null && request.getParameter("username").equals("") != true)
            this.username = request.getParameter("username");
        if (request.getParameter("password") != null && request.getParameter("password").equals("") != true)
            this.password = request.getParameter("password");
        if (request.getParameter("realname") != null && request.getParameter("realname").equals("") != true)
            this.realname = request.getParameter("realname");
        if (request.getParameter("number") != null && request.getParameter("number").equals("") != true)
            this.number = request.getParameter("number");
        if (request.getParameter("identity") != null && request.getParameter("identity").equals("") != true)
            this.identity = Integer.parseInt(request.getParameter("identity"));
    }

    public static List<User> toUsers(ResultSet resultSet) {
        List<User> list = new ArrayList<User>();
        try {
            while (resultSet.next()) {
                User user = new User();
                user.id = resultSet.getInt("id");
                user.username = resultSet.getString("username");
                user.password = resultSet.getString("password");
                user.realname = resultSet.getString("realname");
                user.number = resultSet.getString("number");
                user.identity = resultSet.getInt("identity");
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
