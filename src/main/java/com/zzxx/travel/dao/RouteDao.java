package com.zzxx.travel.dao;

import com.zzxx.travel.domain.Route;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RouteDao {
    @Select("select * from tab_route where cid = #{cid}")
    List<Route> findAllByCid(Integer cid);
}
