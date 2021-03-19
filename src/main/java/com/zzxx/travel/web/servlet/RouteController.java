package com.zzxx.travel.web.servlet;

import com.github.pagehelper.PageInfo;
import com.zzxx.travel.domain.Route;
import com.zzxx.travel.service.RouteService;
import javafx.scene.input.RotateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/route")
public class RouteController {
    @Autowired
    private RouteService routeService;

    @RequestMapping("/queryPage")
    @ResponseBody
    public PageInfo<Route> queryPage(Integer cid, Integer pageSize, Integer currentPage) throws IOException {
        pageSize = pageSize == null ? 8 : pageSize;
        currentPage = currentPage == null ? 1 : currentPage;
        // 调用service获得pageBean对象
        PageInfo<Route> pageInfo = routeService.findByPage(cid, currentPage, pageSize);
        // 将结果序列化为json返回
        return pageInfo;
    }
    @RequestMapping("/detail")
    public Route detail(Integer rid) throws IOException {
        // 调用service获得route对象
        return routeService.findOne(rid);
    }
}
