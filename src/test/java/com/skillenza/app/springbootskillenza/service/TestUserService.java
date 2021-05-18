package com.skillenza.app.springbootskillenza.service;

import com.skillenza.app.springbootskillenza.model.AuthCredential;
import com.skillenza.app.springbootskillenza.model.User;
import com.skillenza.app.springbootskillenza.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
class TestUserService {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private static User user;

    @BeforeAll
    static void setUp() {
        user = new User(UUID.randomUUID(), "abc@abc.com", "abcPassword", "ABC", "DEF");
    }

    @Test
    void addUserTest() {
        User testUser = new User(UUID.randomUUID(), "xyz@email.com", "abcPassword", "ABC", "DEF");

        Mockito.when(userService.getUserByEmail(user.getEmail())).thenReturn(null);
        Mockito.when(userRepository.addUser(user)).thenReturn(user);
        Mockito.when(userService.getUserByEmail(testUser.getEmail())).thenReturn(testUser);

        User addedUser = userService.addUser(user);
        User failedAddedUser = userService.addUser(testUser);
        Assertions.assertEquals(user, addedUser);
        Assertions.assertNull(failedAddedUser);
    }

    @Test
    void getUserByIdTest() {
        UUID invalidId = UUID.randomUUID();
        Mockito.when(userRepository.getUserById(invalidId)).thenReturn(null);
        Mockito.when(userRepository.getUserById(user.getId())).thenReturn(user);

        User testUser = userService.getUserById(user.getId());
        User failedUser = userService.getUserById(invalidId);
        Assertions.assertEquals(user, testUser);
        Assertions.assertNull(failedUser);
    }

    @Test
    void getUserByEmailTest() {
        String email = "xyz@email.com";
        Mockito.when(userRepository.getUserByEmail(email)).thenReturn(null);
        Mockito.when(userRepository.getUserByEmail(user.getEmail())).thenReturn(user);

        User testUser = userService.getUserByEmail(user.getEmail());
        User failedUser = userService.getUserByEmail(email);
        Assertions.assertEquals(user, testUser);
        Assertions.assertNull(failedUser);
    }

    @Test
    void updateUserById() {
        UUID invalidId = UUID.randomUUID();
        User updatedUser = new User(user.getId(), "abc@abc.com", "abcPassword", "ABCD", "EFGH");

        Mockito.when(userService.getUserById(user.getId())).thenReturn(user);
        Mockito.when(userRepository.updateUserById(updatedUser)).thenReturn(updatedUser);
        Mockito.when(userRepository.getUserById(invalidId)).thenReturn(null);

        User testUser = userService.updateUserById(user.getId(), updatedUser);
        User failedUser = userService.updateUserById(invalidId, updatedUser);
        Assertions.assertEquals(updatedUser, testUser);
        Assertions.assertNull(failedUser);
    }

    @Test
    void successfulLoginTest() {
        UUID id = UUID.randomUUID();
        AuthCredential incomingAuthCredential = new AuthCredential(id, "abc@abc.com", "abcPassword");
        AuthCredential existingAuthCredential = new AuthCredential(id, "abc@abc.com", "$2a$04$d.GtqdXt.8e3dkARTxmmrueLnJONuMLa7DbGYZSGCY70aHqyGrMky");

        Mockito.when(userService.getUserByEmail(incomingAuthCredential.getEmail())).thenReturn(user);
        Mockito.when(userRepository.getAuthCredentialByEmail(incomingAuthCredential.getEmail())).thenReturn(existingAuthCredential);

        User testUser = userService.loginService(incomingAuthCredential.getEmail(), incomingAuthCredential.getPassword());
        Assertions.assertEquals(user, testUser);

    }

    @Test
    void unsuccessfulLoginTest() {
        UUID id = UUID.randomUUID();
        AuthCredential incomingAuthCredential = new AuthCredential(id, "abc@abc.com", "abcPassword");
        AuthCredential incomingAuthCredential2 = new AuthCredential(id, "abcd@abc.com", "abcPassword");
        AuthCredential existingAuthCredential = new AuthCredential(id, "abc@abc.com", PasswordService.hashPassword("abcdPassword"));

        Mockito.when(userService.getUserByEmail(incomingAuthCredential.getEmail())).thenReturn(user);
        Mockito.when(userRepository.getAuthCredentialByEmail(incomingAuthCredential.getEmail())).thenReturn(existingAuthCredential);
        Mockito.when(userRepository.getUserByEmail(incomingAuthCredential2.getEmail())).thenReturn(null);

        User failedUser1 = userService.loginService(incomingAuthCredential.getEmail(), incomingAuthCredential.getPassword());
        User failedUser2 = userService.loginService(incomingAuthCredential2.getEmail(), incomingAuthCredential2.getPassword());
        Assertions.assertNull(failedUser1);
        Assertions.assertNull(failedUser2);
    }
}
