package cn.itcast.travel.service;

public interface FavoriteService {
    /**
     * 判断是否收藏
     * @param parseInt
     * @param uid
     * @return
     */
    Boolean isFavorite(int parseInt, int uid);

    /**
     * 添加收藏
     * @param parseInt
     * @param uid
     */
    void addFavorite(int parseInt, int uid);
}
