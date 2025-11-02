package com.example.dao.interfaces;

import com.example.models.User;

import java.util.Collection;

public interface IUserDao {

    Collection<User> getUsers(String id, String login, String name, String surname);

}
