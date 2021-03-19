package com.zzxx.travel.dao;

import com.zzxx.travel.domain.Category;
import com.zzxx.travel.domain.Route;
import com.zzxx.travel.domain.Seller;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RouteDao {
    @Select("select * from tab_route where cid = #{cid}")
    List<Route> findAllByCid(Integer cid);

    @Select("select * from tab_route where rid = #{rid}")
    @Results(id = "routeMap", value = {
            @Result(id = true, column = "rid", property = "rid"),
            @Result(column = "rname", property = "rname"),
            @Result(column = "price", property = "price"),
            @Result(column = "routeIntroduce", property = "routeIntroduce"),
            @Result(column = "rflag", property = "rflag"),
            @Result(column = "rdate", property = "rdate"),
            @Result(column = "isThemeTour", property = "isThemeTour"),
            @Result(column = "rimage", property = "rimage"),
            @Result(column = "rid", property = "count", javaType = Integer.class, many = @Many(select = "com.zzxx.travel.dao.FavoriteDao.findCountByRid")),
            @Result(column = "cid", property = "category", javaType = Category.class, one = @One(select = "com.zzxx.travel.dao.CategoryDao.findOne")),
            @Result(column = "sid", property = "seller", javaType = Seller.class, one = @One(select = "com.zzxx.travel.dao.SellerDao.findById")),
            @Result(column = "rid", property = "routeImgList", javaType = List.class, many = @Many(select = "com.zzxx.travel.dao.RouteImgDao.findListByRid")),
    })
    Route findById(Integer rid);
}
