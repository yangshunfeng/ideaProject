package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.BaseServlet;
import util.SendJsonUtil;


@SuppressWarnings("serial")
@WebServlet(name = " UserLogout",
        urlPatterns = "/user/logout")
public class UserServletLogout extends BaseServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        clear(request);
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("result", 1);
        SendJsonUtil.send(response, res);
    }
}
