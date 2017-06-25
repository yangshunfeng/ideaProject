package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Apply;
import model.Record;
import model.User;
import service.ApplyService;
import service.RecordService;
import util.ArrayUtil;
import util.BaseServlet;
import util.SendJsonUtil;

@SuppressWarnings("serial")
@WebServlet(name = " ApplyDelete",
        urlPatterns = "/apply/delete")
public class ApplyServletDelete extends BaseServlet {
    public String ids;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setparm(request);
        Map<String, Object> res = new HashMap<String, Object>();
        String msg = check(request);
        Integer result = 0;
        if (msg.equals("")) {
            List<Integer> applyidlists = ArrayUtil.StringToArray(ids, ",");
            for (int i = 0; i < applyidlists.size(); i++) {
                Apply applycondition = new Apply();
                applycondition.id = applyidlists.get(i);
                Apply apply = ApplyService.selectBycondition(applycondition, null, null, null, null).get(0);
                Record recordcondition = new Record();
                recordcondition.vote_id = apply.vote_id;
                recordcondition.aim_id = apply.user_id;
                RecordService.deleteBycondition(recordcondition);
            }
            result = ApplyService.deleteByIds(ids);
            msg += "<p>成功删除" + result + "条，失败" + (applyidlists.size() - result) + "条</p>";
        }
        res.put("result", result);
        res.put("msg", msg);
        SendJsonUtil.send(response, res);
    }

    public void setparm(HttpServletRequest request) {
        if (request.getParameter("ids") != null && request.getParameter("ids").equals("") != true)
            this.ids = request.getParameter("ids");
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
