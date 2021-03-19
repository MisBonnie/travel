package com.zzxx.travel.service.impl;

import com.zzxx.travel.dao.UserDao;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.exception.LoginException;
import com.zzxx.travel.service.UserService;
import com.zzxx.travel.util.MailUtils;
import com.zzxx.travel.util.Md5Util;
import com.zzxx.travel.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User login(String username, String password) throws Exception {
        password = Md5Util.encodeByMd5(password);
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

    @Override
    public boolean registerUser(User user) {
        try {
            // 设置账号未激活N - 激活Y
            user.setStatus("N");
            // 设置唯一的code
            user.setCode(UuidUtil.getUuid());
            user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
            // 保存这个账号信息
            userDao.saveUser(user);

            // 发送激活邮件
            String text = "<a href='http://localhost:80/user/active?code="+user.getCode()+"'>账号激活</a>";
            MailUtils.sendMail(user.getEmail(), text, "指针旅游网账号激活");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkUserExist(String username) {
        User user = userDao.findUserByUsername(username);
        return user != null;
    }

    @Override
    public boolean active(String code) {
        userDao.updateUserStatus(code);
        return true;
    }
}
