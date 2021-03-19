package com.zzxx.travel.web.servlet;

import com.zzxx.travel.domain.ResultInfo;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.exception.LoginException;
import com.zzxx.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    @ResponseBody
    public ResultInfo login(String username, String password, String check, HttpSession session) {
        // 1.1获取session中的验证码
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        // 1.2对比验证码
        ResultInfo info = new ResultInfo();
        if (check == null || !check.equalsIgnoreCase(checkcode_server)) {
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
        } else {
            try {
                User login = userService.login(username, password);
                session.setAttribute("loginUser", login);
                info.setFlag(true);
            } catch (LoginException e) {
                info.setFlag(false);
                info.setErrorMsg(e.getMessage());
            } catch (Exception e) {
                info.setFlag(false);
                info.setErrorMsg("未知错误");
            }
        }
        return info;
    }

    // 查询登录信息
    @RequestMapping("/findOne")
    @ResponseBody
    public User findOne(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        return user;
    }

    // 退出
    @RequestMapping("/logout")
    public String logout(HttpSession session)  {
        session.invalidate();
        return "redirect:/login.html";
    }

    // 注册账号
    @RequestMapping("/register")
    @ResponseBody
    public ResultInfo register(User user) {
        // 1.调用service进行注册
        boolean flag = userService.registerUser(user);
        // 2.结果判断, 封装结果数据
        ResultInfo info = new ResultInfo();
        info.setFlag(flag);
        if (!flag) {
            info.setErrorMsg("注册失败!");
        }
        // 3.将结果数据返回个客户端
        return info;
    }
    // 检查用户名是否存在
    @RequestMapping("/checkUser")
    @ResponseBody
    public ResultInfo checkUser(String username) throws IOException {
        // 调用service检查用户是否存在 存在-true, 不存在-false
        boolean flag = userService.checkUserExist(username);
        // 创建返回给前端的数据对象
        ResultInfo info = new ResultInfo();
        // 设置flag为通过,不通过
        info.setFlag(!flag);
        return info;
    }

    // 激活账号
    @RequestMapping("/active")
    public void active (String code, HttpServletResponse response) throws IOException {
        // 调用service激活
        boolean flag = userService.active(code);

        // 返回结果数据 - 页面 - 登录页面
        response.setContentType("text/html;charset=utf-8");
        if (flag) {
            response.getWriter().write("激活成功, 请<a href='/login.html'>登录</a>");
        } else {
            response.getWriter().write("激活失败! 请联系管理员!");
        }
    }
}
