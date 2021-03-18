package com.zzxx.travel.service.impl;

import com.zzxx.travel.dao.CategoryDao;
import com.zzxx.travel.domain.Category;
import com.zzxx.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ShardedJedisPool shardedJedisPool;
    @Override
    public List<Category> findAll() {
        ShardedJedis jedis = shardedJedisPool.getResource();
        // 1.从redis缓存中查询数据
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        if (categorys == null || categorys.size() == 0) {
            System.out.println("从数据库查询....");
            // 1.2查不到数据, 从数据库中查询并返回
            List<Category> list = categoryDao.findAll();
            // 2.第一次从数据库中查询出来的数据,存储到redis缓存中
            for(Category cate:list) {
                jedis.zadd("category", cate.getCid(), cate.getCname());
            }
            jedis.close();
            return list;
        } else {
            System.out.println("从redis查询....");
            // 1.1查到数据, 直接将数据封装返回
            List<Category> categories = new ArrayList<>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCid((int) tuple.getScore());
                category.setCname(tuple.getElement());
                categories.add(category);
            }
            return categories;
        }
    }
}
