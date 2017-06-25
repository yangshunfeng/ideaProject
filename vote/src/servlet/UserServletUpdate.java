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
@WebServlet(name = "UserUpdate",
        urlPatterns = "/user/update")
public class UserServletUpdate extends BaseServlet {
    public User user;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setparm(request);
        Map<String, Object> res = new HashMap<String, Object>();
        String msg = check(request);
        Integer result = 0;
        if (msg.equals("")) {
            result = UserService.updateById(user);
            if (result == 0) {
                msg += "<p>用户修改失败！</p>";
            } else {
                msg += "<p>用户修改成功！</p>";
            }
        }
        res.put("result", result);
        res.put("msg", msg);
        SendJsonUtil.send(response, res);
    }

    public void setparm(HttpServletRequest request) {
        this.user = new User(request);
        if (user.password != null) user.password = MD5Util.encode(user.password);
    }

    public String check(HttpServletRequest request) {
        String msg = new String();
        User loginuser = (User) get(request, "user");
        if (loginuser.identity == 1) {
            msg += "</p>权限不足！</p>";
        }
        User usercondition = new User();
        usercondition.id = user.id;
        List<User> userlists = UserService.selectBycondition(usercondition, null, null, null, null);
        if (userlists.size() == 0) {
            msg += "</p>此用户不存在！</p>";
        } else {
            User userago = userlists.get(0);
            if (!userago.username.equals(user.username)) {
                usercondition.id = null;
                usercondition.username = user.username;
                userlists = UserService.selectBycondition(usercondition, null, null, null, null);
                if (userlists.size() != 0) {
                    msg += "</p>此用户名已存在！</p>";
                }
            }
        }
        return msg;
    }
}
