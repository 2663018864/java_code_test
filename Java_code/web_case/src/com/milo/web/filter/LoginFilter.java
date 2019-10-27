package com.milo.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.http.HttpRequest;

@WebFilter("/*")
public class LoginFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //把ServletRequest转化为HttpRequest
        HttpServletRequest request = (HttpServletRequest) req;
        //获取URI
        String uri = request.getRequestURI();
        //判断是否访问的是登陆资源
        if (uri.contains("/login.jsp") || uri.contains("/loginServlet") || uri.contains("/checkCodeServlet") || uri.contains("/css/") || uri.contains("/js/") || uri.contains("/fonts/")) {
            //放行
            chain.doFilter(request, resp);
        } else {
            //判断是否已经登陆
            Object user = request.getSession().getAttribute("user");
            if (user != null) {
                //已经登陆，放行
                chain.doFilter(request, resp);
            } else {
                //还没有登陆，跳转到登陆页面
                request.setAttribute("login_msg", "您还没有登陆，请先登录");
                request.getRequestDispatcher("/login.jsp").forward(request, resp);
            }
        }

    }

    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
