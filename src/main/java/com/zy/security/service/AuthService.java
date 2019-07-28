package com.zy.security.service;

import com.zy.security.entity.User;

/**
 * @Auther: zy
 * @Date: 20190331 21:26
 * @Description:
 */
public interface AuthService {
    User register(User userToAdd);
    String login(String username, String password);
    String refresh(String oldToken);
}
