package com.milo.test;

import com.milo.dao.UserDao;
import com.milo.dao.impl.UserDaoImpl;
import com.milo.domain.User;
import org.junit.Test;

import java.util.List;

public class UserDaoImplTest {
    private UserDao userDao = new UserDaoImpl();
    @Test
    public void testFindAll() {
        List<User> users = userDao.findAll();
        System.out.println(users);
    }

    @Test
    public void testLogin() {
        User user = userDao.login("zhangsan", "123");
        System.out.println(user);
    }

    @Test
    public void testAddUser() {
        User user = new User(null, "milo", "男", 18, "西安", "111111", "milo@aa", null, null);
        userDao.addUser(user);
    }

    @Test
    public void testFindUserById() {
        User user = userDao.findUserById(1);
        System.out.println(user);
    }

    @Test
    public void testUpdateUser() {
        User updateUser = new User(4, "milo", "女", 16, "广东", "11111122222", "Milo@ada", null, null);
        userDao.updateUser(updateUser);
    }

    @Test
    public void testFindTotalCount() {
        /*int totalCount = userDao.findTotalCount(condition);
        System.out.println(totalCount);*/
    }

    @Test
    public void testFindUserByPage() {
        /*List<User> users = userDao.findUserByPage(10, 5, condition);
        System.out.println(users);*/
    }
}
