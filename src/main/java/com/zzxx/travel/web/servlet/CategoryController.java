package com.zzxx.travel.web.servlet;

import com.zzxx.travel.domain.Category;
import com.zzxx.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("/listCategory")
    public List<Category> findAll() {
        return categoryService.findAll();
    }
}
