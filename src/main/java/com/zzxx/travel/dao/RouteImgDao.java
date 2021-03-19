package com.zzxx.travel.dao;

import com.zzxx.travel.domain.RouteImg;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RouteImgDao {
    @Select("select * from tab_route_img where rid = #{rid}")
    List<RouteImg> findListByRid(int rid);
}
