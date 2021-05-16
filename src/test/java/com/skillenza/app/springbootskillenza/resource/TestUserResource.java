package com.skillenza.app.springbootskillenza.resource;

import com.skillenza.app.springbootskillenza.model.AuthCredential;
import com.skillenza.app.springbootskillenza.model.User;
import com.skillenza.app.springbootskillenza.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
class TestUserResource {

    @Autowired
    private UserResource userResource;

    @MockBean
    private UserService userService;

    private static User user;

    @BeforeAll
    static void setUp() {
        user = new User(UUID.randomUUID(), "abc@abc.com", "abcPassword", "ABC", "DEF");
    }

    @Test
    void addUserTest() {
        Mockito.when(userService.addUser(user)).thenReturn(user);

        ResponseEntity<Object> response = userResource.addUser(user);
        Assertions.assertEquals(user, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void addUserWithEmailTest() {
        User testUser = new User(UUID.randomUUID(), "abcxyz", "abcPassword", "ABC", "DEF");

        ResponseEntity<Object> response = userResource.addUser(testUser);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getUserByIdTest() {
        UUID invalidId = UUID.randomUUID();
        Mockito.when(userService.getUserById(invalidId)).thenReturn(null);
        Mockito.when(userService.getUserById(user.getId())).thenReturn(user);

        ResponseEntity<Object> invalidResponse = userResource.getUserById(invalidId);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, invalidResponse.getStatusCode());

        ResponseEntity<Object> validResponse = userResource.getUserById(user.getId());
        Assertions.assertEquals(HttpStatus.OK, validResponse.getStatusCode());
        Assertions.assertEquals(user, validResponse.getBody());
    }

    @Test
    void loginUserTest() {
        AuthCredential correctCredential = new AuthCredential(UUID.randomUUID(), "abc@abc.com", "abcPassword");
        AuthCredential incorrectCredential = new AuthCredential(UUID.randomUUID(), "abc@abc.com", "abcP");

        Mockito.when(userService.loginService(correctCredential.getEmail(), correctCredential.getPassword())).thenReturn(user);
        Mockito.when(userService.loginService(incorrectCredential.getEmail(), incorrectCredential.getPassword())).thenReturn(null);

        ResponseEntity<Object> invalidResponse = userResource.loginUser(incorrectCredential);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, invalidResponse.getStatusCode());

        ResponseEntity<Object> validResponse = userResource.loginUser(correctCredential);
        Assertions.assertEquals(HttpStatus.OK, validResponse.getStatusCode());
        Assertions.assertEquals(user, validResponse.getBody());
    }

}
