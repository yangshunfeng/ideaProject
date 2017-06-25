package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import model.User;
import service.UserService;
import util.BaseServlet;
import util.MD5Util;
import util.SendJsonUtil;

@SuppressWarnings("serial")
@WebServlet(name = " UserUpload",
        urlPatterns = "/user/upload")
public class UserServletUpload extends BaseServlet {
    public String filePath; // 文件存放目录
    public String tempPath; // 临时文件目录

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = getServletContext();
        filePath = context.getRealPath("uploadFile");
        tempPath = context.getRealPath("temp");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> res = new HashMap<String, Object>();
        String msg = check(request);
        Integer result = 0;
        if (msg.equals("")) {
            try {
                DiskFileItemFactory diskFactory = new DiskFileItemFactory();
                diskFactory.setSizeThreshold(4 * 1024);
                diskFactory.setRepository(new File(tempPath));
                ServletFileUpload upload = new ServletFileUpload(diskFactory);
                upload.setSizeMax(1024 * 1024 * 1024);
                List<FileItem> fileItems = upload.parseRequest(request);
                for (int i = 0; i < fileItems.size(); i++) {
                    FileItem item = (FileItem) fileItems.get(i);
                    if (!item.isFormField()) {
                        String userfilename = processUploadFile(item);
                        result += saveuser(userfilename);
                    }
                }
                msg += "<p>成功添加" + result + "个用户";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        res.put("result", result);
        res.put("msg", msg);
        SendJsonUtil.send(response, res);
    }

    public String check(HttpServletRequest request) {
        String msg = new String();
        User loginuser = (User) get(request, "user");
        if (loginuser.identity == 1) {
            msg += "</p>权限不足！</p>";
        }
        return msg;
    }

    private String processUploadFile(FileItem item) throws Exception {
        String filename = item.getName();
        int index = filename.lastIndexOf("\\");
        filename = filename.substring(index + 1, filename.length());
        long fileSize = item.getSize();
        if ("".equals(filename) && fileSize == 0) {
            return "-1";
        }
        File uploadFile = new File(filePath + "/" + filename);
        item.write(uploadFile);
        return filePath + "/" + filename;
    }

    public Integer saveuser(String userfilename) throws BiffException, FileNotFoundException, IOException {
        Workbook workbook = Workbook.getWorkbook(new FileInputStream(new File(userfilename)));
        Sheet sheet = workbook.getSheet(0);
        Integer sum = 0;
        for (int i = 1; i < sheet.getRows(); i++) {
            Cell[] cells = sheet.getRow(i);
            User user = new User();
            user.username = cells[0].getContents();
            user.password = cells[1].getContents();
            user.password = MD5Util.encode(user.password);
            user.realname = cells[2].getContents();
            user.number = cells[3].getContents();
            user.identity = Integer.parseInt(cells[4].getContents());
            User usercondition = new User();
            usercondition.username = user.username;
            List<User> userlists = UserService.selectBycondition(usercondition, null, null, null, null);
            if (userlists.size() != 0) continue;
            sum += UserService.insert(user);
        }
        return sum;
    }
}
