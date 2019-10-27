package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/favorite/*")
public class FavoriteServlet extends BaseServlet {
    private FavoriteService service = new FavoriteServiceImpl();
    /**
     * 判断用户是否收藏线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid
        String rid = request.getParameter("rid");
        //获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断用户是否登录
        int uid = 0;
        if (user != null) {
            uid = user.getUid();
        }
        //调用service的方法
        Boolean flag = service.isFavorite(Integer.parseInt(rid), uid);
        //发送响应信息
        writeValue(flag, response);

    }

    /**
     * 收藏功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid
        String rid = request.getParameter("rid");
        //获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断用户是否登录
        int uid = 0;
        if (user != null) {
            //用户已登录
            service.addFavorite(Integer.parseInt(rid), user.getUid());
        }
    }
}
