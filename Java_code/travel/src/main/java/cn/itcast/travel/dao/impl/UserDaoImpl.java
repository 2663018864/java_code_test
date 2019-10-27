package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        //定义sql
        try {
            String sql = "select * from tab_user where username = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
            return user;
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * 添加用户
     * @param registerUser
     */
    @Override
    public void addUser(User registerUser) {
        //定义sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        //执行sql
        template.update(sql, registerUser.getUsername(),
                            registerUser.getPassword(),
                            registerUser.getName(),
                            registerUser.getBirthday(),
                            registerUser.getSex(),
                            registerUser.getTelephone(),
                            registerUser.getEmail(),
                            registerUser.getStatus(),
                            registerUser.getCode());
    }

    /**
     * 通过code修改用户的status
     * @param code
     * @return
     */
    @Override
    public Boolean updateByCode(String code) {
        //定义sql
        String sql = "update tab_user set status = 'Y' where code = ?";
        int i = template.update(sql, code);
        //判断是否修改成功
        if (i == 1) {
            return true;
        } else{
            return false;
        }
    }

    /**
     * 通过用户名和密码查询用户
     * @param loginUser
     * @return
     */
    @Override
    public User findUserByUsernameAndPassword(User loginUser) {
        try {
            String sql = "select * from tab_user where username = ? and password = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), loginUser.getUsername(), loginUser.getPassword());
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
