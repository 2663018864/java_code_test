package cn.itcast.travel.service.impl;


import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    /**
     * 查询旅游线路
     * @param currentPage
     * @param pageSize
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public PageBean<Route> findRouteLine(int currentPage, int pageSize, int cid, String rname) {
        //创建pageBean对象，封装数据
        PageBean<Route> pb = new PageBean<Route>();
        //查询总记录数
        int totalCount = routeDao.findTotalCount(cid, rname);
        pb.setTotalCount(totalCount);
        //计算总页数
        int totalPage = (totalCount % pageSize) == 0 ? (totalCount / pageSize) : (totalCount / pageSize + 1);
        //判断当前页是否大于总页数
        if (currentPage > totalPage) {
            currentPage = totalPage;
        }
        //判断当前页是否小于等于0
        if (currentPage <= 0) {
            currentPage = 1;
        }
        //查询每页显示的数据
        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findByPage(cid, start, pageSize, rname);
        pb.setList(list);
        //封装当前页码，和每页展示的条数
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        pb.setTotalPage(totalPage);
        return pb;
    }

    /**
     * 查询旅游线路的详细信息
     * @param rid
     * @return
     */
    @Override
    public Route findRouteInfor(String rid) {
        //1查询route的基本信息
        Route route = routeDao.findRouteByRid(rid);
        if (route != null) {
            //2查询route的图片信息
            List<RouteImg> routeImgList = routeImgDao.findRouteImgByRid(route.getRid());
            route.setRouteImgList(routeImgList);
            //查询route的商家信息
            Seller seller = sellerDao.findRouteSellerBySid(route.getSid());
            route.setSeller(seller);
            //查询旅游线路收藏的次数
            int count = favoriteDao.findFavoriteCount(rid);
            route.setCount(count);
            return  route;
        }
            return null;
    }

}
