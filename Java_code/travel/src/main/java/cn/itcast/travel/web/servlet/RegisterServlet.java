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

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
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
            mapper.writeValue(response.getWriter(), info);
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
        UserService service = new UserServiceImpl();
        boolean flag = service.registerUser(registerUser);
        if (flag) {
            //添加用户成功
            info.setFlag(true);
            mapper.writeValue(response.getWriter(), info);
        } else {
            //添加失败,给页面发送错误信息
            info.setFlag(false);
            info.setErrorMsg("该用户名已被使用");
            mapper.writeValue(response.getWriter(), info);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
