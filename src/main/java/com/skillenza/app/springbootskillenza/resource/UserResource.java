package com.skillenza.app.springbootskillenza.resource;

import com.skillenza.app.springbootskillenza.model.AuthCredential;
import com.skillenza.app.springbootskillenza.model.User;
import com.skillenza.app.springbootskillenza.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/user")
public class UserResource {

    private static final String ERROR_MESSAGE = "An internal server error occurred!";

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        try {
            if (!checkEmail(user.getEmail())) {
                return new ResponseEntity<>("Email is invalid", HttpStatus.BAD_REQUEST);
            }
            User addedUser = userService.addUser(user);
            if (addedUser == null) {
                return new ResponseEntity<>("User with email: " + user.getEmail() + " already exists.", HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(addedUser, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable UUID id) {
        try {
            User user = userService.getUserById(id);
            if (user == null) {
                return new ResponseEntity<>("User with ID: " + id + " doesn't exist.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserById(@PathVariable UUID id, @RequestBody User user) {
        try {
            if (!id.equals(user.getId())) {
                return new ResponseEntity<>("ID in the path and ID in the body must be the same.", HttpStatus.BAD_REQUEST);
            }
            User updatedUser = userService.updateUserById(id, user);
            if (updatedUser == null) {
                return new ResponseEntity<>("Update Failed: User with ID: " + id + " doesn't exist.", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody AuthCredential authCredential) {
        try {
            User loggedInUser = userService.loginService(authCredential.getEmail(), authCredential.getPassword());
            if (loggedInUser == null) {
                return new ResponseEntity<>("Incorrect Credentials", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
