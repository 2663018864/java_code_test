package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     * 用户注册方法
     * @param registerUser
     * @return
     */
    public boolean registerUser(User registerUser);

    /**
     * 激活用户方法
     * @param code
     * @return
     */
    Boolean activeUser(String code);

    /**
     * 登录方法
     * @param loginUser
     * @return
     */
    User loginUser(User loginUser);
}
