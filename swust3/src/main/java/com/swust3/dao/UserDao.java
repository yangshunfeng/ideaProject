package com.swust3.dao;

import com.swust3.domain.User;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(Integer uid);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(User record);

    List<User> selectByCondition(User record, Integer page, Integer rows);
}