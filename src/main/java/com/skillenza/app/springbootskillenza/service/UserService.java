package com.skillenza.app.springbootskillenza.service;

import com.skillenza.app.springbootskillenza.dbhelper.UserDbHelper;
import com.skillenza.app.springbootskillenza.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserDbHelper userDbHelper;

    public User addUser(User user) {
        if (getUserByEmail(user.getEmail()) != null) {
            LOGGER.warn("SignUp failed: User with email: {} already exists!", user.getEmail());
            return null;
        }
        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(PasswordService.hashPassword(user.getPassword()));
        return userDbHelper.adduser(user);
    }

    public User getUserByEmail(String email) {
        return userDbHelper.getUserByEmail(email);
    }

    public User loginService(String email, String password) {
        User user = getUserByEmail(email);
        if (user == null) {
            LOGGER.warn("Login Failed: User with email: {} doesn't exist!", email);
            return null;
        }
        if (!PasswordService.checkPassword(password, user.getPassword())) {
            LOGGER.warn("Login Failed: User with email: {} provided invalid password", email);
            return null;
        }
        LOGGER.info("User with email: {} successfully logged in.", email);
        return user;
    }
}
