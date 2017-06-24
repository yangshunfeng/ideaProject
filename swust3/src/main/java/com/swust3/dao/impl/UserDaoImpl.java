package com.swust3.dao.impl;

import com.swust3.dao.CommonDao;
import com.swust3.dao.UserDao;
import com.swust3.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class UserDaoImpl extends CommonDao implements UserDao {
    @Override
    public int deleteByPrimaryKey(Integer uid) {
        if (uid == null) {
            return 0;
        }
        try {
            UserDao userDao = getSqlSession().getMapper(UserDao.class);
            return userDao.deleteByPrimaryKey(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int insertSelective(User record) {
        if (record == null) {
            return 0;
        }
        record.setUid(null);
        try {
            UserDao userDao = getSqlSession().getMapper(UserDao.class);
            return userDao.insertSelective(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public User selectByPrimaryKey(Integer uid) {
        if (uid == null) {
            return null;
        }
        try {
            UserDao userDao = getSqlSession().getMapper(UserDao.class);
            return userDao.selectByPrimaryKey(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        if (record == null) {
            return 0;
        }
        if (record.getUid() == null) {
            return 0;
        }
        try {
            UserDao userDao = getSqlSession().getMapper(UserDao.class);
            return userDao.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<User> selectByCondition(User record, Integer page, Integer rows) {
        if (record == null) {
            record = new User();
        }
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("record", record);
            param.put("start", page == null || rows == null ? null : (page - 1) * rows);
            param.put("rows", page == null || rows == null ? null : rows);
            String string = "com.csTest.dao.UserDao.selectByCondition";
            return getSqlSession().selectList(string, param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<User>();
    }
}
