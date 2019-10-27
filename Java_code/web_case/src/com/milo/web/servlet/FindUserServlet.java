package com.milo.web.servlet;

import com.milo.domain.User;
import com.milo.service.UserService;
import com.milo.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String id = request.getParameter("id");
        //调用service，返回user
        UserService service = new UserServiceImpl();
        User user = service.findUser(id);
        //跳转到update.jsp
        request.setAttribute("user", user);
        request.getRequestDispatcher("/update.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
