package listener;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import util.BaseServlet;

@SuppressWarnings("serial")
@WebListener
public class ApplicationListener extends BaseServlet implements ServletContextListener {

    public static ServletContext application;

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();
        String filename = application.getRealPath("");
        File filePath = new File(filename, "uploadFile");
        File tempPath = new File(filename, "temp");
        File excelPath = new File(filename, "excelfiles");
        if (!filePath.isDirectory())
            filePath.mkdir();
        if (!tempPath.isDirectory())
            tempPath.mkdir();
        if (!excelPath.isDirectory())
            excelPath.mkdir();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();
        Connection conn = (Connection) application.getAttribute("conn");
        if (conn != null) {
            try {
                conn.close();
                System.out.println("数据库成功断开");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
