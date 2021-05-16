package com.skillenza.app.springbootskillenza.service;

import com.skillenza.app.springbootskillenza.model.AuthCredential;
import com.skillenza.app.springbootskillenza.model.User;
import com.skillenza.app.springbootskillenza.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public User addUser(User user) {
        if (getUserByEmail(user.getEmail()) != null) {
            LOGGER.warn("SignUp failed: User with email: {} already exists!", user.getEmail());
            return null;
        }
        user.setPassword(PasswordService.hashPassword(user.getPassword()));
        return userRepository.addUser(user);
    }

    public User getUserById(UUID id) {
        return userRepository.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public User updateUserById(UUID id, User user) {
        if (getUserById(id) == null) {
            LOGGER.error("Update Failed! User with ID: {} was not found.", id);
            return null;
        }
        LOGGER.info("Update Success: User with ID: {} successfully updated.", id);
        return userRepository.updateUserById(user);
    }

    public User loginService(String email, String password) {
        User user = getUserByEmail(email);
        if (user == null) {
            LOGGER.warn("Login Failed: User with email: {} doesn't exist!", email);
            return null;
        }
        AuthCredential credential = userRepository.getAuthCredentialByEmail(email);
        if (!PasswordService.checkPassword(password, credential.getPassword())) {
            LOGGER.warn("Login Failed: User with email: {} provided invalid password", email);
            return null;
        }
        LOGGER.info("User with email: {} successfully logged in.", email);
        return user;
    }
}
