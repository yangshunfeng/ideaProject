package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.VoteInfo;
import service.VoteInfoService;
import util.BaseServlet;
import util.SendJsonUtil;


@SuppressWarnings("serial")
@WebServlet(name = "VoteInfoUpdate",
        urlPatterns = "/voteinfo/update")
public class VoteInfoServletUpdate extends BaseServlet {
    public VoteInfo voteInfo;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setparm(request);
        Map<String, Object> res = new HashMap<String, Object>();
        String msg = check(request);
        Integer result = 0;
        if (msg.equals("")) {
            result = VoteInfoService.updateById(voteInfo);
            if (result == 0) {
                msg += "<p>投票项目修改失败！</p>";
            } else {
                msg += "<p>投票项目修改成功！</p>";
            }
        }
        res.put("result", result);
        res.put("msg", msg);
        SendJsonUtil.send(response, res);
    }

    public void setparm(HttpServletRequest request) {
        this.voteInfo = new VoteInfo(request);
    }

    public String check(HttpServletRequest request) {
        String msg = new String();
        User loginuser = (User) get(request, "user");
        if (loginuser.identity == 1) {
            msg += "</p>权限不足！</p>";
        }
        VoteInfo voteInfocondition = new VoteInfo();
        voteInfocondition.id = voteInfo.id;
        List<VoteInfo> voteInfolists = VoteInfoService.selectBycondition(voteInfocondition, null, null, null, null);
        if (voteInfolists.size() == 0) {
            msg += "</p>此投票项目不存在！</p>";
        }
        return msg;
    }
}
