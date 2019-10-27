package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    /**
     * 查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    public int findTotalCount(int cid, String rname);

    /**
     * 分页查询旅游线路
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    public List<Route> findByPage(int cid, int start, int pageSize, String rname);

    /**
     * 查询旅游线路通过rid
     * @param rid
     * @return
     */
    Route findRouteByRid(String rid);
}
