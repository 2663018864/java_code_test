package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {

    /**
     * 查询旅游线路的商家信息
     * @param sid
     * @return
     */
    public Seller findRouteSellerBySid(int sid);
}
