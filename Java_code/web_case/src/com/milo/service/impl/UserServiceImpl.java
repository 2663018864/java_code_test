package com.milo.service.impl;

import com.milo.dao.UserDao;
import com.milo.dao.impl.UserDaoImpl;
import com.milo.domain.BeanPage;
import com.milo.domain.User;
import com.milo.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();

    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public User login(User loginUser) {
        return dao.login(loginUser.getUserName(), loginUser.getPassword());
    }

    @Override
    public void addUser(User user) {
        dao.addUser(user);
    }

    @Override
    public void deleteUser(String id) {
        dao.deleteById(Integer.parseInt(id));
    }

    @Override
    public User findUser(String id) {
        return dao.findUserById(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        dao.updateUser(user);
    }

    @Override
    public void delSelect(String[] ids) {
        if (ids != null && ids.length > 0) {
            for (String id : ids) {
                dao.deleteById(Integer.parseInt(id));
            }
        }
    }

    @Override
    public BeanPage<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        //翻页按钮翻到第一页时再往前翻时显示第一页
        if (currentPage <= 0) {
            currentPage = 1;
        }
        //创建BeanPage对象
        BeanPage<User> bp = new BeanPage<>();
        bp.setCurrentPage(currentPage);
        bp.setRows(rows);
        //查询总记录数
        int totalCount = dao.findTotalCount(condition);
        bp.setTotalCount(totalCount);
        //计算总页数
        int totalPage = totalCount % rows == 0 ? totalCount/rows : totalCount/rows + 1;
        bp.setTotalPage(totalPage);
        //查询每页展示的条数
        int start = (currentPage - 1) * rows;
        List<User> list = dao.findUserByPage(start, rows, condition);
        bp.setList(list);
        return bp;
    }
}
