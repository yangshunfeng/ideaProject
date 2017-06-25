package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Apply;
import model.User;
import model.VoteInfo;
import service.ApplyService;
import service.UserService;
import service.VoteInfoService;
import util.BaseServlet;
import util.SendJsonUtil;

@SuppressWarnings("serial")
@WebServlet(name = " ApplyInsert",
        urlPatterns = "/apply/insert")
public class ApplyServletInsert extends BaseServlet {
    public Apply apply;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setparm(request);
        Map<String, Object> res = new HashMap<String, Object>();
        String msg = check(request);
        Integer result = 0;
        if (msg.equals("")) {
            result = ApplyService.insert(apply);
            if (result == 0) {
                msg += "<p>申请提交失败！</p>";
            } else {
                msg += "<p>申请提交成功！</p>";
            }
        }
        res.put("result", result);
        res.put("msg", msg);
        SendJsonUtil.send(response, res);
    }

    public void setparm(HttpServletRequest request) {
        this.apply = new Apply(request);
    }

    public String check(HttpServletRequest request) {
        String msg = new String();
        User loginuser = (User) get(request, "user");
        User usercondition = new User();
        usercondition.id = apply.user_id;
        List<User> userlists = UserService.selectBycondition(usercondition, null, null, null, null);
        if (userlists.size() == 0) {
            msg += "<p>此用户不存在！</p>";
        }
        VoteInfo voteInfocondition = new VoteInfo();
        voteInfocondition.id = apply.vote_id;
        List<VoteInfo> voteInfolists = VoteInfoService.selectBycondition(voteInfocondition, null, null, null, null);
        if (voteInfolists.size() == 0) {
            msg += "<p>此投票项目不存在！</p>";
        } else if (voteInfolists.get(0).state == 2) {
            msg += "<p>此投票项目正在投票！</p>";
        } else if (voteInfolists.get(0).state == 3) {
            msg += "<p>此投票项目已结束！</p>";
        }
        if (loginuser.identity == 1 && loginuser.id != apply.user_id) {
            msg += "<p>权限不足！</p>";
        }
        if (loginuser.identity == 1 && apply.is_accept == 1) {
            msg += "<p>权限不足！</p>";
        }
        Apply applycondition = new Apply();
        applycondition.user_id = apply.user_id;
        applycondition.vote_id = apply.vote_id;
        List<Apply> applylists = ApplyService.selectBycondition(applycondition, null, null, null, null);
        if (applylists.size() != 0) {
            msg += "<p>此用户已申请过此项目！</p>";
        }
        return msg;
    }
}
