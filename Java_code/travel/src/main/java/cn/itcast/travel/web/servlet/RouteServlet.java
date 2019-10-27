package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    RouteService service = new RouteServiceImpl();
    /**
     * 旅游线路展示功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showRouteLine(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //获取参数
        //获取当前页
        String currentPage_str = request.getParameter("currentPage");
        //获取分类id
        String cid_str = request.getParameter("cid");
        //获取搜索关键词
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        //获取每页显示的条数
        String pageSize_str = request.getParameter("pageSize");
//        System.out.println(cid_str);
//        System.out.println(rname);
        //判断参数是否有值
        int currentPage = 0;
        if (currentPage_str == null || currentPage_str.length() == 0) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(currentPage_str);
        }

        int cid = 0;
        if (cid_str != null && cid_str.length() > 0 && !"null".equals(cid_str)) {
            cid = Integer.parseInt(cid_str);
        }

        int pageSize = 0;
        if (pageSize_str == null || pageSize_str.length() == 0) {
            pageSize = 5;
        } else {
            pageSize = Integer.parseInt(pageSize_str);
        }

        //调用service的分页查询方法查询
        PageBean<Route> pb = service.findRouteLine(currentPage, pageSize, cid, rname);
        //把返回的数据转换为json发送
        writeValue(pb, response);
    }

    /**
     * 展示旅游线路的详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showRouteInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取线路的rid
        String rid = request.getParameter("rid");
        if (rid != null && rid.length() > 0) {
            //调用service方法查询线路的详细信息
            Route routeInfo = service.findRouteInfor(rid);
            //响应数据
            writeValue(routeInfo, response);
        }
    }

}
