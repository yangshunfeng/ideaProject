package servlet;

import java.io.IOException;
import java.util.HashMap;
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
@WebServlet(name = "VoteInfoInsert",
        urlPatterns = "/voteinfo/insert")
public class VoteInfoServletInsert extends BaseServlet {
    public VoteInfo voteInfo;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setparm(request);
        Map<String, Object> res = new HashMap<String, Object>();
        String msg = check(request);
        Integer result = 0;
        if (msg.equals("")) {
            result = VoteInfoService.insert(voteInfo);
            if (result == 0) {
                msg += "<p>投票项目创建失败！</p>";
            } else {
                msg += "<p>投票项目创建成功！</p>";
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
        return msg;
    }
}
