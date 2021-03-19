package com.zzxx.travel.service.impl;

import com.zzxx.travel.dao.UserDao;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.exception.LoginException;
import com.zzxx.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User login(String username, String password) throws LoginException {
        User user = userDao.findByUsernameAndPassword(username, password);
        if (user == null) {
            // 账号/密码错误
            throw new LoginException("账号/密码错误");
        } else if (user.getStatus().equals("N")) {
            // 账号未激活
            throw new LoginException("账号未激活");
        } else  {
            return user;
        }
    }
}
