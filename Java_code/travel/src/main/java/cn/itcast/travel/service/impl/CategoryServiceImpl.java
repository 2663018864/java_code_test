package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao dao = new CategoryDaoImpl();
    /**
     * 查询所有的类别
     * @return
     */
    @Override
    public List<Category> findAll() {
        //先在redis中查询
        Jedis jedis = JedisUtil.getJedis();
        //查询所有数据包括score
        Set<Tuple> categories = jedis.zrangeWithScores("categories", 0, -1);
        //如果redis中没有数据，则在数据库中查询
        List<Category> list = new ArrayList<Category>();
        if(categories == null || categories.size() == 0) {
            //从数据库中查询数据
            list = dao.findAll();
//            System.out.println(list);
            //把数据存储到redis中
            for (Category category : list) {
//                System.out.println(category.getCname());
//                System.out.println(category.getCid());
                jedis.zadd("categories", category.getCid(), category.getCname());
            }
        } else {
            for (Tuple category : categories) {
                //将categorie的数据存储到集合中
                Category c = new Category();
                c.setCid((int)category.getScore());
                c.setCname(category.getElement());
//                System.out.println(category.getElement());
                list.add(c);
            }
        }
        return list;
    }
}
