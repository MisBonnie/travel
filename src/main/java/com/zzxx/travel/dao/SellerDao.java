package com.zzxx.travel.dao;

import com.zzxx.travel.domain.Seller;
import org.apache.ibatis.annotations.Select;

public interface SellerDao {
    @Select("select * from tab_seller where sid = #{sid}")
    Seller findById(int sid);
}
