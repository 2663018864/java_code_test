package com.milo.dao.impl;

import com.milo.dao.UserDao;
import com.milo.domain.User;
import com.milo.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<User> findAll() {
        //定义sql
        String sql = "select * from user";
        //查询
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public User login(String userName, String password) {
        try {
            String sql = "select * from user where username = ? and password = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), userName, password);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addUser(User user) {
        String sql = "insert into user values(null, ?, ?, ?, ?, ?, ?, null, null)";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail());
    }

    @Override
    public void deleteById(int id) {
        String sql = "delete from user where id = ?";
        template.update(sql, id);
    }

    @Override
    public User findUserById(int id) {
        try {
            String sql = "select * from user where id = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
            return user;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateUser(User user) {
        String sql = "update user set name = ?, gender = ?, age = ?, address = ?, qq = ?, email = ? where id = ?";
        template.update(sql, user.getName(), user.getGender(), user.getAge(), user.getAddress(), user.getQq(), user.getEmail(), user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        String sql = "select count(id) from user where 1 = 1";
        List<Object> params = new ArrayList<>();
        sql = getConditionSql(sql, condition, params).toString();
//        System.out.println(sql);
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<User> findUserByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from user where 1 = 1";
        List<Object> params = new ArrayList<>();
        StringBuilder sb = getConditionSql(sql, condition, params);
        sql = sb.append(" limit ? , ?").toString();
//        System.out.println(sql);
        params.add(start);
        params.add(rows);
        return template.query(sql, new BeanPropertyRowMapper<User>(User.class), params.toArray());
    }

    /**
     * 根据初始化的sql获取条件查询的sql
     * @param sql 初始化的sql
     * @param condition 条件的map集合
     * @param params 保存条件的值的集合
     * @return 条件查询的sql
     */
    private StringBuilder getConditionSql(String sql, Map<String, String[]> condition, List<Object> params) {
        StringBuilder sb = new StringBuilder(sql);
        Set<String> keySet = condition.keySet();
        for (String key : keySet) {
            //获取value
            String value = condition.get(key)[0];
            //排除currentPage和rows
            if ("currentPage".equals(key) || "rows".equals(key)) {
                continue;
            }
            //判断是否有条件查询的参数，有则拼接在sql后面，并保存value
            if (value != null && !"".equals(value)) {
                sb.append(" and " + key + " like ? ");
                params.add("%" + value + "%");
            }
        }
        return sb;
    }
}
