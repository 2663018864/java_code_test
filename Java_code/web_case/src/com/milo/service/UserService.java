package com.milo.service;

import com.milo.domain.BeanPage;
import com.milo.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 查询所有用户
     * @return
     */
    List<User> findAll();

    /**
     * 登录方法
     * @param loginUser
     * @return
     */
    User login(User loginUser);

    /**
     * 添加用户
     * @param user
     */
    void addUser(User user);

    /**
     * 删除用户
     * @param id
     */
    void deleteUser(String id);

    /**
     * 通过id查找用户
     * @param id
     * @return
     */
    User findUser(String id);

    /**
     * 修改用户信息
     * @param user
     */
    void updateUser(User user);

    /**
     * 批量删除
     * @param ids
     */
    void delSelect(String[] ids);

    /**
     * 条件分页查询
     * @param currentPage
     * @param rows
     * @param condition
     * @return
     */
    BeanPage<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);
}
