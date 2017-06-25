package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import model.User;
import service.UserService;
import util.BaseServlet;
import util.SendJsonUtil;

@SuppressWarnings("serial")
@WebServlet(name = " UserExport",
        urlPatterns = "/user/export")
public class UserServletExport extends BaseServlet {
    public User user;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setparm(request);
        Map<String, Object> res = new HashMap<String, Object>();
        String msg = check(request);
        Integer result = 0;
        if (msg.equals("")) {
            List<User> userlists = UserService.selectBycondition(user, null, null, null, null);
            WritableWorkbook book = null;
            WritableSheet sheet = null;
            Label label = null;
            File file = new File(request.getSession().getServletContext().getRealPath("excelfiles"), "exportuser.xls");
            if (!file.exists()) file.createNewFile();
            OutputStream os = new FileOutputStream(file.getPath());
            book = Workbook.createWorkbook(os);
            sheet = book.createSheet("第一页", 0);
            try {
                label = new Label(0, 0, "序号");
                sheet.addCell(label);
                label = new Label(1, 0, "Id");
                sheet.addCell(label);
                label = new Label(2, 0, "用户名");
                sheet.addCell(label);
                label = new Label(3, 0, "姓名");
                sheet.addCell(label);
                label = new Label(4, 0, "学号");
                sheet.addCell(label);
                label = new Label(5, 0, "权限");
                sheet.addCell(label);
            } catch (WriteException e) {
                e.printStackTrace();
            }
            int count = 1;
            for (int i = 0; i < userlists.size(); i++) {
                try {
                    jxl.write.Number number = new jxl.write.Number(0, count, count);
                    sheet.addCell(number);
                    label = new Label(1, count, userlists.get(i).id.toString());
                    sheet.addCell(label);
                    label = new Label(2, count, userlists.get(i).username);
                    sheet.addCell(label);
                    label = new Label(3, count, userlists.get(i).realname);
                    sheet.addCell(label);
                    label = new Label(4, count, userlists.get(i).number);
                    sheet.addCell(label);
                    label = new Label(5, count, userlists.get(i).identity == 1 ? "普通用户" : "管理员");
                    sheet.addCell(label);
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                count++;
            }
            book.write();
            try {
                book.close();
                os.close();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            result = 1;
        }
        res.put("result", result);
        res.put("msg", msg);
        SendJsonUtil.send(response, res);
    }

    public void setparm(HttpServletRequest request) {
        this.user = new User(request);
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
