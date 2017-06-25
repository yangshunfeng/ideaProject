package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import service.UserService;
import util.BaseServlet;
import util.MD5Util;
import util.SendJsonUtil;

@SuppressWarnings("serial")
@WebServlet(name = "UserInsert",
        urlPatterns = "/user/insert")
public class UserServletInsert extends BaseServlet {
    public User user;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setparm(request);
        Map<String, Object> res = new HashMap<String, Object>();
        String msg = check(request);
        Integer result = 0;
        if (msg.equals("")) {
            result = UserService.insert(user);
            if (result == 0) {
                msg += "<p>用户创建失败！</p>";
            } else {
                msg += "<p>用户创建成功！</p>";
            }
        }
        res.put("result", result);
        res.put("msg", msg);
        SendJsonUtil.send(response, res);
    }

    public void setparm(HttpServletRequest request) {
        this.user = new User(request);
        user.password = MD5Util.encode(user.password);
    }

    public String check(HttpServletRequest request) {
        String msg = new String();
        User loginuser = (User) get(request, "user");
        if (loginuser.identity == 1) {
            msg += "</p>权限不足！</p>";
        }
        User usercondition = new User();
        usercondition.username = user.username;
        List<User> userlists = UserService.selectBycondition(usercondition, null, null, null, null);
        if (userlists.size() != 0) {
            msg += "</p>此用户名已存在！</p>";
        }
        return msg;
    }
}

