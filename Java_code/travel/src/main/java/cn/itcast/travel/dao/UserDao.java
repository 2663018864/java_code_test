package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    public User findUserByUsername(String username);

    /**
     * 添加用户
     * @param registerUser
     */
    void addUser(User registerUser);

    /**
     * 通过code修改用户的status
     * @param code
     * @return
     */
    Boolean updateByCode(String code);

    /**
     * 通过用户名和密码查询用户
     * @param loginUser
     * @return
     */
    User findUserByUsernameAndPassword(User loginUser);
}
