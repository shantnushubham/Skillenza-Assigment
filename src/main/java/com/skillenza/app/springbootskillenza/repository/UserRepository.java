package com.skillenza.app.springbootskillenza.repository;

import com.mongodb.client.MongoCollection;
import com.skillenza.app.springbootskillenza.mapper.AuthCredentialMapper;
import com.skillenza.app.springbootskillenza.mapper.UserMapper;
import com.skillenza.app.springbootskillenza.model.AuthCredential;
import com.skillenza.app.springbootskillenza.model.User;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    private final UserMapper userMapper;
    private final AuthCredentialMapper authCredentialMapper;
    private final MongoCollection<Document> userCollection;
    private final MongoCollection<Document> credentialCollection;

    @Autowired
    public UserRepository(MongoTemplate mongoTemplate, UserMapper userMapper, AuthCredentialMapper authCredentialMapper) {
        this.userCollection = mongoTemplate.getCollection("users");
        this.credentialCollection = mongoTemplate.getCollection("credentials");
        this.userMapper = userMapper;
        this.authCredentialMapper = authCredentialMapper;
    }

    public User addUser(User user) {
        user.setId(UUID.randomUUID());
        AuthCredential credential = new AuthCredential(UUID.randomUUID(), user.getEmail(), user.getPassword());
        credentialCollection.insertOne(authCredentialMapper.authCredentialToDocument(credential));
        Document document = userMapper.userToDocument(user);
        userCollection.insertOne(document);
        LOGGER.info("New User with ID: {} and email: {} and corresponding Auth Credentials are added to the database.", user.getId(), user.getEmail());
        return userMapper.documentToUser(document);
    }

    public User getUserById(UUID id) {
        Document document = userCollection.find(new Document("_id", id.toString())).first();
        if (document == null) {
            LOGGER.error("User with ID: {} doesn't exist!", id);
            return null;
        }
        LOGGER.info("User with ID: {} was successfully found.", id);
        return userMapper.documentToUser(document);
    }

    public User getUserByEmail(String email) {
        Document document = userCollection.find(new Document("email", email)).first();
        if (document == null) {
            LOGGER.error("User with email: {} doesn't exist!", email);
            return null;
        }
        LOGGER.info("User with email: {} was successfully found.", email);
        return userMapper.documentToUser(document);
    }

    public User updateUserById(User user) {
        Document document = userMapper.userToDocument(user);
        userCollection.updateOne(new Document("_id", user.getId().toString()), new Document("$set", document));
        LOGGER.info("User with ID: {} was successfully updated in Database", user.getId());
        return user;
    }

    public AuthCredential getAuthCredentialByEmail(String email) {
        Document document = credentialCollection.find(new Document("email", email)).first();
        if (document == null) {
            LOGGER.error("Auth Credential of email: {} does not exist.", email);
            return null;
        }
        LOGGER.info("Auth Credential of email: {} was successfully fetched.", email);
        return authCredentialMapper.documentToAuthCredential(document);
    }

    public AuthCredential updateAuthCredential(AuthCredential authCredential) {
        Document document = authCredentialMapper.authCredentialToDocument(authCredential);
        credentialCollection.updateOne(new Document("email", authCredential.getEmail()), new Document("$set", document));
        LOGGER.info("Auth Credential of email: {} was successfully updated.", authCredential.getEmail());
        return authCredential;
    }
}
