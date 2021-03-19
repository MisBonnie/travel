package com.zzxx.travel.service;

import com.github.pagehelper.PageInfo;
import com.zzxx.travel.domain.Route;

public interface RouteService {
    PageInfo<Route> findByPage(Integer cid, Integer currentPage, Integer pageSize);

    Route findOne(Integer rid);
}
