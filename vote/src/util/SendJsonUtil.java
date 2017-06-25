package util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SendJsonUtil {

    public static void send(HttpServletResponse response, Object obj) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = null;
        if (obj instanceof String) json = (String) obj;
        else json = gson.toJson(obj);
        PrintWriter out = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("cache-control", "no-cache");
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.write(json);
        out.flush();
        out.close();
    }

}
