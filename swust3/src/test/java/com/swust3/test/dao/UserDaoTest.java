package com.swust3.test.dao;

import com.swust3.dao.UserDao;
import com.swust3.domain.User;
import com.swust3.test.JunitBaseServiceDaoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDaoTest extends JunitBaseServiceDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void insertSelective() {
        User user = new User("yangshunfeng", "123");
        System.out.println(userDao.insertSelective(user));
    }

    @Test
    public void selectByPrimaryKey() {
        User user = userDao.selectByPrimaryKey(4);
        System.out.println(user);
    }

    @Test
    public void updateByPrimaryKeySelective() {
        User user = new User("yangshunfeng", "456");
        user.setUid(2);
        System.out.println(userDao.updateByPrimaryKeySelective(user));
    }

    @Test
    public void selectCondition() {
        User user = new User("yang", null);
        for (User temp : userDao.selectByCondition(user, null, null)) {
            System.out.println(temp);
        }
    }

    @Test
    public void deleteByPrimaryKey() {
        System.out.println(userDao.deleteByPrimaryKey(1));
    }
}
