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

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();
    /**
     * 注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        //创建响应信息的封装对象
        ResultInfo info = new ResultInfo();
        //获取用户输入的验证码
        String checkCode = request.getParameter("check");
        //获取随机生成的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //从session中删除验证码，保证验证码的唯一性
        session.removeAttribute("CHECKCODE_SERVER");
        //判断验证码是否正确
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(checkCode)) {
            //发送验证码错误信息到客户端
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            writeValue(info, response);
            return;
        }
        //获取表单数据,封装成对象
        Map<String, String[]> map = request.getParameterMap();
        User registerUser = new User();
        try {
            BeanUtils.populate(registerUser, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //通过service注册用户
        service = new UserServiceImpl();
        boolean flag = service.registerUser(registerUser);
        if (flag) {
            //添加用户成功
            info.setFlag(true);
            writeValue(info, response);
        } else {
            //添加失败,给页面发送错误信息
            info.setFlag(false);
            info.setErrorMsg("该用户名已被使用");
            writeValue(info, response);
        }
    }

    /**
     * 登陆功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        //获取用户输入的验证码
        String checkCode = request.getParameter("check");
        //获取生成的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //保证验证码的唯一性
        session.removeAttribute("CHECKCODE_SERVER");
        //创建封装响应信息的对象
        ResultInfo info = new ResultInfo();
        //判断验证码是否正确
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(checkCode)) {
            //验证码错误，发送错误信息
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            writeValue(info, response);
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
        service = new UserServiceImpl();
        User user = service.loginUser(loginUser);
        //判断是否登录成功
        if (user == null) {
            //登录失败,发送错误信息
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
            writeValue(info, response);
            return;
        }
        //判断用户是否激活
        if (!"Y".equals(user.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("您的用户尚未激活，请先激活");
            writeValue(info, response);
            return;
        }
        //登录成功
        info.setFlag(true);
        //存储用户信息
        session.setAttribute("user", user);
        writeValue(info, response);
    }

    /**
     * 激活用户功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取数据
        String code = request.getParameter("code");
        //调用service的激活方法
        service = new UserServiceImpl();
        Boolean flag = service.activeUser(code);
        //判断是否激活
        String msg;
        if (flag) {
            //激活成功
            msg = "激活成功，请<a href='login.html'>登录</a>";
        } else {
            //激活失败
            msg = "激活失败，请联系管理员";
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
    }

    /**
     * 登陆成功后展示用户名的功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showUserName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        //获取session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //响应到页面用户名
        if (user != null) {
            writeValue(user.getName(), response);
        } else {
            writeValue(null, response);
        }
    }

    /**
     * 退出登陆功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //销毁session
        request.getSession().invalidate();
        //重定向到登陆页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }
}
