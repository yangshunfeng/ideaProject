package util;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void save(HttpServletRequest request, Map<String, Object> param) {
        HttpSession session = request.getSession();
        for (Entry<String, Object> entry : param.entrySet()) {
            session.setAttribute(entry.getKey(), entry.getValue());
        }
    }


    public void save(HttpServletRequest request, String paramName, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(paramName, value);
    }


    public Object get(HttpServletRequest request, String paramName) {
        HttpSession session = request.getSession();
        return session.getAttribute(paramName);
    }


    public void clear(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }


    public String getIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
