package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 查询所有的类别
     * @return
     */
    public List<Category> findAll();
}
