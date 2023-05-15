package com.geekbrains.demoboot.services;

import com.geekbrains.demoboot.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;

public interface UserService extends UserDetailsService {
    User findByUserName(String name);
    void saveUser(String name, String password, String email, String s);

    User principalGetUser(Principal principal);
}
