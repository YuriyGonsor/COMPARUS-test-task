package com.example.services.interfaces;

import com.example.models.User;

import java.util.Collection;

public interface IUserService {

    Collection<User> getUsers(String id, String login, String name, String surname);

}
