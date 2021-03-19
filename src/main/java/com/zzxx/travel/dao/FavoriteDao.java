package com.zzxx.travel.dao;

import org.apache.ibatis.annotations.Select;

public interface FavoriteDao {
    @Select("select count(*) from tab_favorite where rid = #{rid}")
    int findCountByRid(int rid);
}
