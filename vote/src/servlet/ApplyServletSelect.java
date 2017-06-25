package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Apply;
import service.ApplyService;
import util.BaseServlet;
import util.SendJsonUtil;

@SuppressWarnings("serial")
@WebServlet(name = " ApplySelect",
        urlPatterns = "/apply/select")
public class ApplyServletSelect extends BaseServlet {
    public Apply apply;
    public Integer page;
    public Integer rows;
    public String sort;
    public String order;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setparm(request);
        Integer total = ApplyService.selectBycondition(apply, null, null, null, null).size();
        List<Apply> applylists = ApplyService.selectBycondition(apply, sort, order, page, rows);
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("rows", applylists);
        res.put("total", total);
        SendJsonUtil.send(response, res);
    }

    public void setparm(HttpServletRequest request) {
        this.apply = new Apply(request);
        if (request.getParameter("page") != null && request.getParameter("page").equals("") != true)
            this.page = Integer.parseInt(request.getParameter("page"));
        if (request.getParameter("rows") != null && request.getParameter("rows").equals("") != true)
            this.rows = Integer.parseInt(request.getParameter("rows"));
        if (request.getParameter("sort") != null && request.getParameter("sort").equals("") != true)
            this.sort = request.getParameter("sort");
        if (request.getParameter("order") != null && request.getParameter("order").equals("") != true)
            this.order = request.getParameter("order");
    }
}
