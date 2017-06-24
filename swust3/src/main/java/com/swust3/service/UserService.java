package com.swust3.service;

import com.swust3.domain.User;

import java.util.Map;

public interface UserService {
    Map<String, Object> selectByUid(Integer uid);

    Map<String, Object> selectByCondition(User user, Integer page, Integer rows);

    Map<String, Object> login(String username, String password);
}
