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
import util.SendJsonUtil;


@SuppressWarnings("serial")
@WebServlet(name = "UserSelect",
        urlPatterns = "/user/select")
public class UserServletSelect extends BaseServlet {
    public User user;
    public Integer page;
    public Integer rows;
    public String sort;
    public String order;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setparm(request);
        Map<String, Object> res = new HashMap<String, Object>();
        String msg = check(request);
        if (msg.equals("")) {
            Integer total = UserService.selectBycondition(user, null, null, null, null).size();
            List<User> userlists = UserService.selectBycondition(user, sort, order, page, rows);
            res.put("total", total);
            res.put("rows", userlists);
        } else {
            res.put("msg", msg);
        }
        SendJsonUtil.send(response, res);
    }

    public void setparm(HttpServletRequest request) {
        this.user = new User(request);
        if (request.getParameter("page") != null && request.getParameter("page").equals("") != true)
            this.page = Integer.parseInt(request.getParameter("page"));
        if (request.getParameter("rows") != null && request.getParameter("rows").equals("") != true)
            this.rows = Integer.parseInt(request.getParameter("rows"));
        if (request.getParameter("sort") != null && request.getParameter("sort").equals("") != true)
            this.sort = request.getParameter("sort");
        if (request.getParameter("order") != null && request.getParameter("order").equals("") != true)
            this.order = request.getParameter("order");
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
