package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {

    /**
     * 通过rid查询旅游线路的图片
     * @param rid
     * @return
     */
    public List<RouteImg> findRouteImgByRid(int rid);
}
