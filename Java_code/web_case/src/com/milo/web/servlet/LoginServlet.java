package com.milo.web.servlet;

import com.milo.domain.User;
import com.milo.service.UserService;
import com.milo.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //获取用户输入的验证码
        String verifycode = request.getParameter("verifycode");
        //获取生成的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        //校验验证码
        //验证码不正确
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(verifycode)) {
            //给出提示信息
            request.setAttribute("login_msg", "验证码错误");
            //将数据转发到页面
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            //结束方法
            return;
        }
        //验证码正确
        //获取请求参数
        Map<String, String[]> map = request.getParameterMap();
        //将参数封装到对象
        User loginUser = new User();
        try {
            BeanUtils.populate(loginUser, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        System.out.println(loginUser);
        //调用方法，验证用户名和密码
        UserService us = new UserServiceImpl();
        User user = us.login(loginUser);
        if (user != null) {
            //登录成功
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            //登录失败
            request.setAttribute("login_msg", "用户名或密码错误");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
