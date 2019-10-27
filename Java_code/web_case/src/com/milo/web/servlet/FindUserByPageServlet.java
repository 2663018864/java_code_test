package com.milo.web.servlet;

import com.milo.domain.BeanPage;
import com.milo.domain.User;
import com.milo.service.UserService;
import com.milo.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //获取当前页数和每页展示的条数
        String currentPage = request.getParameter("currentPage");
        String rows = request.getParameter("rows");
        //当没有获取到值时，设置当前页为第一页
        if (currentPage == null || "".equals(currentPage)) {
            currentPage = "1";
        }
        if (rows == null || "".equals(rows)) {
            rows = "5";
        }
        //获取条件查询的参数
        Map<String, String[]> condition = request.getParameterMap();
        //调用service方法进行带条件的分页查询
        UserService service = new UserServiceImpl();
        BeanPage<User> bp = service.findUserByPage(currentPage, rows, condition);
        //转发数据到list.jsp
        request.setAttribute("BeanPage", bp);
        request.setAttribute("condition", condition);
        request.getRequestDispatcher("/list.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
