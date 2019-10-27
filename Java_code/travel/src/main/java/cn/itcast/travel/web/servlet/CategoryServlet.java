package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService service = new CategoryServiceImpl();
    /**
     * 展示所有的类别
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void showCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //调用service方法查询所有的类别
        List<Category> list = service.findAll();
        //将list集合转换为json对象，发送
//        System.out.println(list);
        writeValue(list, response);
    }

}
