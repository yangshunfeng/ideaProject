package com.swust3.service.impl;

import com.swust3.dao.UserDao;
import com.swust3.domain.User;
import com.swust3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Map<String, Object> selectByUid(Integer uid) {
        Map<String, Object> res = new HashMap<String, Object>();
        User user = userDao.selectByPrimaryKey(uid);
        res.put("user", user);
        if (user == null) {
            res.put("status", 0);
        } else {
            res.put("status", 1);
        }
        return res;
    }

    @Override
    public Map<String, Object> selectByCondition(User user, Integer page, Integer rows) {
        Map<String, Object> res = new HashMap<String, Object>();
        List<User> userList = userDao.selectByCondition(user, page, rows);
        res.put("userList", userList);
        return res;
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> res = new HashMap<String, Object>();
        List<User> userList = userDao.selectByCondition(null, null, null);
        boolean exit = false;
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            if (username.equals(user.getUsername())) {
                exit = true; // 用户名存在
                if (password.equals(user.getPassword())) {
                    res.put("status", 1);// 登录成功
                    return res;
                }
            }
        }
        if (exit == false) {
            res.put("status", -1);// 用户名不存在
        } else {
            res.put("status", -2);// 密码错误
        }
        return res;
    }
}
