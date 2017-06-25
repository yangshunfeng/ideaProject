package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Apply;
import model.Record;
import model.User;
import model.VoteInfoDetail;
import service.ApplyService;
import service.RecordService;
import service.UserService;
import util.BaseServlet;
import util.SendJsonUtil;


@SuppressWarnings("serial")
@WebServlet(name = "VoteInfoDetail",
        urlPatterns = "/voteinfo/detail")
public class VoteInfoServletDetail extends BaseServlet {
    public Apply apply;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setparm(request);
        List<Apply> applylists = ApplyService.selectBycondition(apply, null, null, null, null);
        String ids = "";
        for (int i = 0; i < applylists.size(); i++) {
            if (i != 0) ids += ",";
            ids += applylists.get(i).user_id;
        }
        List<User> userlists = UserService.selectByids(ids);
        List<VoteInfoDetail> voteInfoDetaillists = new ArrayList<VoteInfoDetail>();
        for (int i = 0; i < userlists.size(); i++) {
            VoteInfoDetail voteInfoDetail = new VoteInfoDetail();
            User user = userlists.get(i);
            voteInfoDetail.id = user.id;
            voteInfoDetail.username = user.username;
            voteInfoDetail.realname = user.realname;
            voteInfoDetail.number = user.number;
            voteInfoDetail.identity = user.identity;
            apply.user_id = user.id;
            apply.vote_id = apply.vote_id;
            Apply applytemp = ApplyService.selectBycondition(apply, null, null, null, null).get(0);
            voteInfoDetail.introduction = applytemp.introduction;
            voteInfoDetail.is_accept = applytemp.is_accept;
            Record record = new Record();
            record.vote_id = apply.vote_id;
            record.aim_id = user.id;
            voteInfoDetail.count = RecordService.selectBycondition(record, null, null, null, null).size();
            voteInfoDetaillists.add(voteInfoDetail);
        }
        SendJsonUtil.send(response, voteInfoDetaillists);
    }

    public void setparm(HttpServletRequest request) {
        this.apply = new Apply(request);
    }
}
