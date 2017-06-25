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
@WebServlet(name = " UserLogin",
        urlPatterns = "/user/login")
public class UserServletLogin extends BaseServlet {
    public User user;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setparm(request);
        Map<String, Object> res = new HashMap<String, Object>();
        String msg = check(request);
        if (msg.equals("")) {
            List<User> userlists = UserService.selectBycondition(user, null, null, null, null);
            if (userlists.size() == 0) {
                msg += "</p>账号或者密码错误！</p>";
                res.put("result", 0);
            } else {
                msg += "</p>登录成功！</p>";
                res.put("result", 1);
                res.put("user", userlists.get(0));
                save(request, "user", userlists.get(0));
            }
        } else res.put("result", 0);
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
        if (loginuser != null) {
            msg += "</p>已进登录，不能再登录！</p>";
        }
        return msg;
    }
}
