package com.zzxx.travel.dao;

import com.zzxx.travel.domain.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryDao {
    @Select("select * from tab_category")
    List<Category> findAll();
}
