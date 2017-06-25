package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import listener.OnlineListener;
import model.User;
import util.BaseServlet;
import util.SendJsonUtil;


@SuppressWarnings("serial")
@WebServlet(name = " UserOnline",
        urlPatterns = "/user/online")
public class UserServletOnline extends BaseServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> userlists = OnlineListener.list;
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("total", userlists.size());
        res.put("rows", userlists);
        SendJsonUtil.send(response, res);
    }
}
