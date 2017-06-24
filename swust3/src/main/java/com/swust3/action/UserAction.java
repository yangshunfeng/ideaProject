package com.swust3.action;

import com.swust3.domain.User;
import com.swust3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserAction extends BaseAction {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/selectByUid", method = {RequestMethod.GET,
            RequestMethod.POST})
    public Map<String, Object> selectByUid(Integer uid) {
        Map<String, Object> res = null;
        res = userService.selectByUid(uid);
        return res;
    }

    @RequestMapping(value = "/user/selectByCondition", method = {
            RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> selectByCondition(Integer uid, String username,
                                                 Integer page, Integer rows) {
        Map<String, Object> res = null;
        res = userService.selectByCondition(new User(uid, username, null), page,
                rows);
        return res;
    }

    @RequestMapping(value = "/user/login", method = {RequestMethod.GET,
            RequestMethod.POST})
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> res = null;
        if (username == null || password == null) {
            res = new HashMap<String, Object>();
            res.put("status", -200);
        } else {
            res = userService.login(username, password);
        }
        return res;
    }

    @RequestMapping(value = "/user/hello", method = {RequestMethod.GET,
            RequestMethod.POST})
    public Map<String, Object> hello() {
        Map<String, Object> res = new HashMap<String, Object>();
        res.put("test", "hello world");
        return res;
    }
}
