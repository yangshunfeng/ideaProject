package filter;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import util.BaseServlet;
import util.SendJsonUtil;

@WebFilter(filterName = "BaseFilter"
        , urlPatterns = {"/user/*", "/voteinfo/*", "/apply/*", "/record/*"})
public class BaseFilter extends BaseServlet implements Filter {
    private static final long serialVersionUID = 1L;
    public FilterConfig config;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long before = System.currentTimeMillis();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpServletRequest requestp = (HttpServletRequest) request;
        User user = (User) get(requestp, "user");
        String url = requestp.getServletPath();
        if (url.equals("/user/login") || url.equals("/user/check") || url.equals("/user/logout") || user != null) {
            chain.doFilter(request, response);
        } else {
            SendJsonUtil.send((HttpServletResponse) response, "非法访问");
        }
        long after = System.currentTimeMillis();
        System.out.print("请求地址：" + requestp.getRequestURI());
        System.out.println("   请求时间：" + time + "   响应时间(MS)：" + (after - before));
    }

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    public void destroy() {
        this.config = null;
    }
}
