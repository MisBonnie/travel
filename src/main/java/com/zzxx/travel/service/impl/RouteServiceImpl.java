package com.zzxx.travel.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzxx.travel.dao.RouteDao;
import com.zzxx.travel.domain.Route;
import com.zzxx.travel.domain.RouteImg;
import com.zzxx.travel.domain.Seller;
import com.zzxx.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteDao routeDao;
    @Override
    public PageInfo<Route> findByPage(Integer cid, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Route> list = routeDao.findAllByCid(cid);
        PageInfo<Route> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Route findOne(Integer rid) {
        // 调用RouteDao 根据 rid查询Route对象
        return routeDao.findById(rid);
    }
}
