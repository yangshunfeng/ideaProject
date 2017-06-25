package listener;

import java.util.ArrayList;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import model.User;

@WebListener
public class OnlineListener implements HttpSessionListener,
        HttpSessionAttributeListener {

    public static ArrayList<User> list = new ArrayList<User>();

    // 新建一个session时触发此操作
    public void sessionCreated(HttpSessionEvent httpEvent) {
        //System.out.println("新建一个session");
    }

    // 销毁一个session时触发此操作
    public void sessionDestroyed(HttpSessionEvent httpEvent) {
        //System.out.println("销毁一个session");
        if (!list.isEmpty()) {
            list.remove(httpEvent.getSession().getAttribute("user"));
        }
    }

    // 添加session中添加对象时触发此操作
    public void attributeAdded(HttpSessionBindingEvent httpEvent) {
        //System.out.println("添加session中添加对象时触发此操作");
        User user = (User) httpEvent.getSession().getAttribute("user");
        user.password = "********";
        list.add(user);
    }

    // 删除session中添加对象时触发此操作
    public void attributeRemoved(HttpSessionBindingEvent httpEvent) {
        //System.out.println("修改、删除session中添加对象时触发此操作");
    }

    // 在session中替换对象时触发此操作
    public void attributeReplaced(HttpSessionBindingEvent httpEvent) {
        //System.out.println("在session中替换对象时触发此操作");
    }
}