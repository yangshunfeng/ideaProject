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
import service.UserService;
import util.ArrayUtil;
import util.BaseServlet;
import util.SendJsonUtil;

@SuppressWarnings("serial")
@WebServlet(name = "UserDelete",
        urlPatterns = "/user/delete")
public class UserServletDelete extends BaseServlet {
    public String ids;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setparm(request);
        Map<String, Object> res = new HashMap<String, Object>();
        String msg = check(request);
        Integer result = 0;
        if (msg.equals("")) {
            List<Integer> useridlists = ArrayUtil.StringToArray(ids, ",");
            for (int i = 0; i < useridlists.size(); i++) {
                Apply applycondition = new Apply();
                applycondition.user_id = useridlists.get(i);
                ApplyService.deleteBycondition(applycondition);
                Record recordcondition = new Record();
                recordcondition.send_id = useridlists.get(i);
                RecordService.deleteBycondition(recordcondition);
                recordcondition.send_id = null;
                recordcondition.aim_id = useridlists.get(i);
                RecordService.deleteBycondition(recordcondition);
            }
            result = UserService.deleteByIds(ids);
            msg += "<p>成功删除" + result + "条，失败" + (useridlists.size() - result) + "条</p>";
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
