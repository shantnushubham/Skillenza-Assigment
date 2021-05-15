package com.skillenza.app.springbootskillenza.resource;

import com.skillenza.app.springbootskillenza.model.User;
import com.skillenza.app.springbootskillenza.service.PasswordService;
import com.skillenza.app.springbootskillenza.service.UserService;
import org.junit.jupiter.api.Assertions;
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

//    @Test
//    void addUserTest() {
//        Map<String, String> userMap = new HashMap<>();
//        userMap.put("email", "abc@abc.com");
//        userMap.put("password", "abcPassword");
//        userMap.put("confirmPassword", "abcPassword");
//
//        UUID id = UUID.randomUUID();
//        User user = new User(id.toString(), userMap.get("email"), PasswordService.hashPassword(userMap.get("password")));
//
//        Mockito.when(userService.getUserByEmail("abc@abc.com")).thenReturn(null);
//        Mockito.when(userService.addUser(new User(null, userMap.get("email"), userMap.get("password")))).thenReturn(user);
//
//        ResponseEntity<Object> response = userResource.addUser(userMap);
//        System.out.println(response.getBody());
//    }

    @Test
    void loginUser() {
        UUID id = UUID.randomUUID();
        Mockito.when(userService.loginService("abc@abc.com", "abcPassword")).thenReturn(new User(id.toString(),
                "abc@abc.com", PasswordService.hashPassword("abcPassword")));

        ResponseEntity<Object> response = userResource.loginUser(new User(null, "abc@abc.com", "abcPassword"));
        User user = (User) response.getBody();
        Assertions.assertTrue(PasswordService.checkPassword("abcPassword", user.getPassword()));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
