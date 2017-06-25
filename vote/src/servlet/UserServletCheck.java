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
@WebServlet(name = " UserCheck",
        urlPatterns = "/user/check")
public class UserServletCheck extends BaseServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("user", get(request, "user"));
        SendJsonUtil.send(response, res);
    }
}
