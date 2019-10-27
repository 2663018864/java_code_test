package com.milo.dao;

import com.milo.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    List<User> findAll();

    User login(String userName, String password);

    void addUser(User user);

    void deleteById(int id);

    User findUserById(int id);

    void updateUser(User user);

    int findTotalCount(Map<String, String[]> condition);

    List<User> findUserByPage(int start, int rows, Map<String, String[]> condition);
}
