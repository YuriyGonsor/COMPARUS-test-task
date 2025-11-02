package com.example.controllers;

import com.example.models.User;
import com.example.services.interfaces.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Collection<User> getUsers(@RequestParam(required = false) String id, @RequestParam(required = false) String login,
                                     @RequestParam(required = false) String name, @RequestParam(required = false) String surname) {
        return userService.getUsers(id, login, name, surname);
    }


}
