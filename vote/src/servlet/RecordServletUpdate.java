package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Apply;
import model.Record;
import model.User;
import model.VoteInfo;
import service.ApplyService;
import service.RecordService;
import service.UserService;
import service.VoteInfoService;
import util.BaseServlet;
import util.SendJsonUtil;

@SuppressWarnings("serial")
@WebServlet(name = "RecordUpdate",
        urlPatterns = "/record/update")
public class RecordServletUpdate extends BaseServlet {
    public Record record;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setparm(request);
        System.out.println(record.toString());
        Map<String, Object> res = new HashMap<String, Object>();
        String msg = check(request);
        Integer result = 0;
        if (msg.equals("")) {
            result = RecordService.updateById(record);
            if (result == 0) {
                msg += "<p>投票记录修改失败！</p>";
            } else {
                msg += "<p>投票记录修改成功！</p>";
            }
        }
        res.put("result", result);
        res.put("msg", msg);
        SendJsonUtil.send(response, res);
    }

    public void setparm(HttpServletRequest request) {
        this.record = new Record(request);
    }

    public String check(HttpServletRequest request) {
        String msg = new String();
        User loginuser = (User) get(request, "user");
        Record recordcondition = new Record();
        recordcondition.id = record.id;
        List<Record> recordlists = RecordService.selectBycondition(recordcondition, null, null, null, null);
        if (recordlists.size() == 0) {
            msg += "<p>此记录不存在！</p>";
        } else {
            Record recordago = recordlists.get(0);
            if (recordago.vote_id != record.vote_id) {
                VoteInfo voteInfocondition = new VoteInfo();
                voteInfocondition.id = record.vote_id;
                List<VoteInfo> voteInfolists = VoteInfoService.selectBycondition(voteInfocondition, null, null, null, null);
                if (voteInfolists.size() == 0) {
                    msg += "<p>此投票项目不存在！</p>";
                } else if (voteInfolists.get(0).state == 1) {
                    msg += "<p>此投票项目正在申请状态中！</p>";
                } else if (voteInfolists.get(0).state == 3) {
                    msg += "<p>此投票项目已结束！</p>";
                }
            }
            if (recordago.aim_id != record.aim_id) {
                User usercondition = new User();
                usercondition.id = record.aim_id;
                List<User> userlists = UserService.selectBycondition(usercondition, null, null, null, null);
                if (userlists.size() == 0) {
                    msg += "<p>被投票用户不存在！</p>";
                } else {
                    Apply applycondition = new Apply();
                    applycondition.vote_id = record.vote_id;
                    applycondition.user_id = record.aim_id;
                    List<Apply> applielists = ApplyService.selectBycondition(applycondition, null, null, null, null);
                    if (applielists.size() == 0) {
                        msg += "<p>被投票用户未申请此项目！</p>";
                    } else if (applielists.get(0).is_accept == 0) {
                        msg += "<p>被投票用户用户的申请未通过！</p>";
                    }
                }
            }
            if (recordago.send_id != record.send_id) {
                User usercondition = new User();
                usercondition.id = record.send_id;
                List<User> userlists = UserService.selectBycondition(usercondition, null, null, null, null);
                if (userlists.size() == 0) {
                    msg += "<p>投票用户不存在！</p>";
                }
            }
            if (loginuser.identity == 1 && (record.send_id != loginuser.id || recordago.send_id != loginuser.id)) {
                msg += "<p>权限不足！</p>";
            }
        }
        return msg;
    }
}
