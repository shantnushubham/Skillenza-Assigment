package com.skillenza.app.springbootskillenza.mapper;

import com.skillenza.app.springbootskillenza.model.User;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {

    public User documentToUser(Document document) {
        User user = new User();
        user.setId(UUID.fromString(document.getString("_id")));
        user.setEmail(document.getString("email"));
        user.setFirstName(document.getString("firstName"));
        user.setLastName(document.getString("lastName"));
        user.setPassword("");
        return user;

    }

    public Document userToDocument(User user) {
        Document document = new Document();
        document.append("_id", user.getId().toString());
        document.append("email", user.getEmail());
        document.append("password", "");
        document.append("firstName", user.getFirstName());
        document.append("lastName", user.getLastName());
        return document;
    }
}
