package com.skillenza.app.springbootskillenza.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.skillenza.app.springbootskillenza.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTService implements Serializable {

    @Value("${jwt.secret}")
    private String secret;

    private static final String ISSUER = "walnut";
    private static final long EXPIRES_IN = 2 * 60 * 60 * 1000L;

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTService.class);

    public String generateUserToken(User user) {
        try {
            long currentMilliseconds = System.currentTimeMillis();
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", user.getId().toString());
            userMap.put("email", user.getEmail());
            String jwt = JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim("user", userMap)
                    .withIssuedAt(new Date(currentMilliseconds))
                    .withExpiresAt(new Date(currentMilliseconds + EXPIRES_IN))
                    .sign(Algorithm.HMAC256(secret));
            LOGGER.info("JWT for User with ID: {} was successfully generated.", user.getId());
            return jwt;
        } catch (JWTCreationException exception) {
            LOGGER.error("Couldn't create JWT for User with ID: {}", user.getId());
            return null;
        }
    }

    public boolean verifyUserToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer(ISSUER)
                    .build();
            verifier.verify(token);
            LOGGER.info("Token was successfully verified");
            return true;
        } catch (JWTVerificationException exception) {
            LOGGER.error("Token provided by the user was not verified.");
            return false;
        }
    }

}
