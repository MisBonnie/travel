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
    private ShardedJedisPool jedisPool;
    @Override
    public List<Category> findAll() {
        ShardedJedis jedis = jedisPool.getResource();

        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        if (category == null || category.size() == 0) {
            System.out.println("从数据库查询...");
            // 1.第一次从数据库中查询数据
            List<Category> list = categoryDao.findAll();
            // 1 门票 2 酒店 3 香港车票 ...
            // 2.将查询出来的数据保存到redis数据库中
            for (Category c : list) {
                jedis.zadd("category", c.getCid(), c.getCname());
            }
            return list;
        } else {
            System.out.println("从redis中查询...");
            // 3.以后查询直接从redis中查询数据
            List<Category> categories = new ArrayList<>();
            for (Tuple tuple : category) {
                Category cate = new Category();
                cate.setCid((int) tuple.getScore());
                cate.setCname(tuple.getElement());
                categories.add(cate);
            }
            return categories;
        }
    }
}
