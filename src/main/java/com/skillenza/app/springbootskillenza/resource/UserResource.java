package com.skillenza.app.springbootskillenza.resource;

import com.skillenza.app.springbootskillenza.model.User;
import com.skillenza.app.springbootskillenza.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserResource {

    private static final String ERROR_MESSAGE = "An internal server error occurred!";

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> addUser(@RequestBody Map<String, String> userMap) {
        try {
            if (!userMap.get("password").equals(userMap.get("confirmPassword"))) {
                return new ResponseEntity<>("Passwords do not match.", HttpStatus.BAD_REQUEST);
            }
            User user = new User(null, userMap.get("email"), userMap.get("password"));
            User addedUser = userService.addUser(user);
            if (addedUser == null) {
                return new ResponseEntity<>("User with email: " + user.getEmail() + " already exists.", HttpStatus.NOT_ACCEPTABLE);
            }
            return new ResponseEntity<>(addedUser, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        try {
            User loggedInUser = userService.loginService(user.getEmail(), user.getPassword());
            if (loggedInUser == null) {
                return new ResponseEntity<>("Incorrect Credentials", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
