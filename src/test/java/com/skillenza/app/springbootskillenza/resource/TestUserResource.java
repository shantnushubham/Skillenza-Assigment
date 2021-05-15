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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
class TestUserResource {

    @Autowired
    private UserResource userResource;

    @MockBean
    private UserService userService;

    @Test
    void checkUser() {
        UUID id = UUID.randomUUID();

        User testUser = new User(id.toString(), "abc@abc.com", "abcPassword");

        Mockito.when(userService.getUserByEmail("abc@abc.com")).thenReturn(testUser);
        Mockito.when(userService.getUserByEmail("xyz@xyz.com")).thenReturn(null);

        User user = userService.getUserByEmail("abc@abc.com");
        User user2 = userService.getUserByEmail("xyz@xyz.com");
        Assertions.assertSame(testUser, user);
        Assertions.assertNull(user2);
    }

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

    @Test
    void validateEmailPassword() {
        Map<String, String> user1 = new HashMap<>();
        user1.put("email", "abc@abc.com");
        user1.put("password", "abcPassword");
        user1.put("confirmPassword", "abcPassword");

        Map<String, String> user2 = new HashMap<>();
        user2.put("email", "xyzxyz");
        user2.put("password", "xyzPassword");
        user2.put("confirmPassword", "xyzPassword");

        Map<String, String> user3 = new HashMap<>();
        user3.put("email", "shant@shant.com");
        user3.put("password", "123");
        user3.put("confirmPassword", "1234");

        ResponseEntity<Object> response = userResource.addUser(user2);
        ResponseEntity<Object> response2 = userResource.addUser(user3);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());

        Assertions.assertTrue(userResource.checkEmail(user1.get("email")));
    }

}
