package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {

    /**
     * 查询旅游线路
     * @param currentPage
     * @param pageSize
     * @param cid
     * @param rname
     * @return
     */
    public PageBean<Route> findRouteLine(int currentPage, int pageSize, int cid, String rname);

    /**
     * 查询旅游线路的详细信息
     * @param rid
     * @return
     */
    Route findRouteInfor(String rid);


}
