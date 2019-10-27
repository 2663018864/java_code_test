package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();
    /**
     * 用户注册方法
     * @param registerUser
     * @return
     */
    @Override
    public boolean registerUser(User registerUser) {
        if (registerUser != null) {
            //通过用户名查询用户
            User userByUsername = dao.findUserByUsername(registerUser.getUsername());
            if (userByUsername == null) {
                //添加用户
                //设置用户的状态为未激活
                registerUser.setStatus("N");
                //设置用户的激活码
                String code = UuidUtil.getUuid();
                registerUser.setCode(code);
                //调用添加用户方法
                dao.addUser(registerUser);
                //发送激活邮件
                String content = "<a href='http://localhost/travel/user/active?code="+registerUser.getCode()+"'>点击激活【黑马旅游网】</a>";
                MailUtils.sendMail(registerUser.getEmail(), content, "激活邮件");
                return true;
            }
        }
        return false;
    }

    /**
     * 激活用户方法
     * @param code
     * @return
     */
    @Override
    public Boolean activeUser(String code) {
        //修改用户的status
        Boolean flag = dao.updateByCode(code);
         if (flag) {
             //激活成功
             return true;
         } else {
             //激活失败
             return false;
         }
    }

    /**
     * 登录方法
     * @param loginUser
     * @return
     */
    @Override
    public User loginUser(User loginUser) {
        if (loginUser != null) {
            User user = dao.findUserByUsernameAndPassword(loginUser);
            return user;
        }
        return null;
    }
}
