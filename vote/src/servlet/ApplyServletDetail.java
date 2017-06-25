package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ApplyDetail;
import model.Record;
import model.User;
import service.RecordService;
import service.UserService;
import util.BaseServlet;
import util.SendJsonUtil;

@SuppressWarnings("serial")
@WebServlet(name = " ApplyDetail",
        urlPatterns = "/apply/detail")
public class ApplyServletDetail extends BaseServlet {
    public Record record;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setparm(request);
        List<Record> recordlists = RecordService.selectBycondition(record, null, null, null, null);
        List<ApplyDetail> applyDetaillists = new ArrayList<ApplyDetail>();
        for (int i = 0; i < recordlists.size(); i++) {
            ApplyDetail applyDetail = new ApplyDetail();
            User user = new User();
            user.id = recordlists.get(i).send_id;
            List<User> userlists = UserService.selectBycondition(user, null, null, null, null);
            if (userlists.size() != 0) {
                applyDetail.introduction = recordlists.get(i).introduction;
                applyDetail.id = userlists.get(0).id;
                applyDetail.identity = userlists.get(0).identity;
                applyDetail.number = userlists.get(0).number;
                applyDetail.realname = userlists.get(0).realname;
                applyDetail.username = userlists.get(0).username;
            }
            user.id = recordlists.get(i).aim_id;
            userlists = UserService.selectBycondition(user, null, null, null, null);
            if (userlists.size() != 0) {
                applyDetail.aim_realname = userlists.get(0).realname;
            }
            applyDetaillists.add(applyDetail);
        }
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("rows", applyDetaillists);
        SendJsonUtil.send(response, res);
    }

    public void setparm(HttpServletRequest request) {
        this.record = new Record(request);
    }
}
