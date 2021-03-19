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

import javax.servlet.http.HttpSession;

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
            }
        }
        return info;
    }
}
