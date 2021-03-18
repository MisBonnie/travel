package com.zzxx.travel.web.servlet;

import com.zzxx.travel.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/login")
    public String login(User user) {
        return "";
    }
}
