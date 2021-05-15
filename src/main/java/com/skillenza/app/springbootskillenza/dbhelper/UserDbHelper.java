package com.skillenza.app.springbootskillenza.dbhelper;

import com.skillenza.app.springbootskillenza.model.User;
import com.skillenza.app.springbootskillenza.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDbHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDbHelper.class);

    @Autowired
    UserRepository userRepository;

    public User adduser(User user) {
        LOGGER.info("New User with User ID: {} and email: {} was added to the database.", user.getUserId(), user.getEmail());
        return userRepository.insert(user);
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            LOGGER.error("User with email: {} doesn't exist.", email);
            return null;
        }
        LOGGER.info("User with email: {} was successfully fetched!", email);
        return userRepository.findUserByEmail(email);
    }
}
