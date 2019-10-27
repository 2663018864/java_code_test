package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    /**
     * 判断是否收藏
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Boolean isFavorite(int rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(rid, uid);
        return favorite != null;
    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void addFavorite(int rid, int uid) {
        //添加收藏
        favoriteDao.add(rid, uid);
    }
}
