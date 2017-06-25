package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Record {
    public Integer id;
    public Integer vote_id;
    public Integer send_id;
    public Integer aim_id;
    public String introduction;

    @Override
    public String toString() {
        return "Record [id=" + id + ", vote_id=" + vote_id + ", send_id=" + send_id + ", aim_id=" + aim_id
                + ", introduction=" + introduction + "]";
    }

    public Record() {
    }

    public Record(HttpServletRequest request) {
        if (request.getParameter("id") != null && request.getParameter("id").equals("") != true)
            this.id = Integer.parseInt(request.getParameter("id"));
        if (request.getParameter("vote_id") != null && request.getParameter("vote_id").equals("") != true)
            this.vote_id = Integer.parseInt(request.getParameter("vote_id"));
        if (request.getParameter("send_id") != null && request.getParameter("send_id").equals("") != true)
            this.send_id = Integer.parseInt(request.getParameter("send_id"));
        if (request.getParameter("aim_id") != null && request.getParameter("aim_id").equals("") != true)
            this.aim_id = Integer.parseInt(request.getParameter("aim_id"));
        if (request.getParameter("introduction") != null && request.getParameter("introduction").equals("") != true)
            this.introduction = request.getParameter("introduction");
    }

    public static List<Record> toRecords(ResultSet resultSet) {
        List<Record> list = new ArrayList<Record>();
        try {
            while (resultSet.next()) {
                Record record = new Record();
                record.id = resultSet.getInt("id");
                record.vote_id = resultSet.getInt("vote_id");
                record.send_id = resultSet.getInt("send_id");
                record.aim_id = resultSet.getInt("aim_id");
                record.introduction = resultSet.getString("introduction");
                list.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
