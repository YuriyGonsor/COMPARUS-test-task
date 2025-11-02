package com.example.services;

import com.example.dao.interfaces.IUserDao;
import com.example.models.User;
import com.example.services.interfaces.IUserService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService implements IUserService {

    private final IUserDao userDao;

    public UserService(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Collection<User> getUsers(String id, String login, String name, String surname) {
        return userDao.getUsers(id, login, name, surname);
    }
}
