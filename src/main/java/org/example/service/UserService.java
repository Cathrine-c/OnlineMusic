package org.example.service;

import org.example.dao.UserDao;
import org.example.entity.User;

public class UserService {
    public User login(User loginUser){
        UserDao userDao = new UserDao();
        User user = userDao.login(loginUser);
        return user;
    }

}
