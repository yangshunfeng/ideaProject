package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Record;
import service.RecordService;
import util.BaseServlet;
import util.SendJsonUtil;

@SuppressWarnings("serial")
@WebServlet(name = "RecordSelect",
        urlPatterns = "/record/select")
public class RecordServletSelect extends BaseServlet {
    public Record record;
    public Integer page;
    public Integer rows;
    public String sort;
    public String order;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setparm(request);
        Integer total = RecordService.selectBycondition(record, null, null, null, null).size();
        List<Record> recordlists = RecordService.selectBycondition(record, sort, order, page, rows);
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("rows", recordlists);
        res.put("total", total);
        SendJsonUtil.send(response, res);
    }

    public void setparm(HttpServletRequest request) {
        this.record = new Record(request);
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
