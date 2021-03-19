package com.zzxx.travel.service;

import com.zzxx.travel.domain.User;
import com.zzxx.travel.exception.LoginException;

public interface UserService {
    User login(String username, String password) throws Exception;

    boolean registerUser(User user);

    boolean checkUserExist(String username);

    boolean active(String code);
}
