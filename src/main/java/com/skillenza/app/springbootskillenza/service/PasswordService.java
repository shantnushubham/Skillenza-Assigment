package com.skillenza.app.springbootskillenza.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    public static String hashPassword(String passwordPlaintext) {
        int workload = 12;
        String salt = BCrypt.gensalt(workload);
        return (BCrypt.hashpw(passwordPlaintext, salt));
    }

    public static boolean checkPassword(String passwordPlaintext, String storedHash) {
        boolean passwordVerified;
        if (null == storedHash || !storedHash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        passwordVerified = BCrypt.checkpw(passwordPlaintext, storedHash);
        return (passwordVerified);
    }

}
