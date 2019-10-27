package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        response.setContentType("application/json;charset=utf-8");
        //获取用户输入的验证码
        String checkCode = request.getParameter("check");
        //获取生成的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //保证验证码的唯一性
        session.removeAttribute("CHECKCODE_SERVER");
        //创建jackson核心对象
        ObjectMapper mapper = new ObjectMapper();
        //创建封装响应信息的对象
        ResultInfo info = new ResultInfo();
        //判断验证码是否正确
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(checkCode)) {
            //验证码错误，发送错误信息
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            mapper.writeValue(response.getWriter(), info);
            return;

        }

        //获取请求信息
        Map<String, String[]> map = request.getParameterMap();
        User loginUser = new User();
        //封装请求数据
        try {
            BeanUtils.populate(loginUser, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service登录方法
        UserService service = new UserServiceImpl();
        User user = service.loginUser(loginUser);
        //判断是否登录成功
        if (user == null) {
            //登录失败,发送错误信息
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
            mapper.writeValue(response.getWriter(), info);
            return;
        }
        //判断用户是否激活
        if (!"Y".equals(user.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("您的用户尚未激活，请先激活");
            mapper.writeValue(response.getWriter(), info);
            return;
        }
        //登录成功
        info.setFlag(true);
        //存储用户信息
        session.setAttribute("user", user);
        mapper.writeValue(response.getWriter(), info);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
