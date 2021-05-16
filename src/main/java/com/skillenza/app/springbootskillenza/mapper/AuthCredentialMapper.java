package com.skillenza.app.springbootskillenza.mapper;

import com.skillenza.app.springbootskillenza.model.AuthCredential;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthCredentialMapper {

    public AuthCredential documentToAuthCredential(Document document) {
        return new AuthCredential(UUID.fromString(document.getString("_id")), document.getString("email"), document.getString("password"));
    }

    public Document authCredentialToDocument(AuthCredential authCredential) {
        Document document = new Document();
        document.append("_id", authCredential.getId().toString());
        document.append("email", authCredential.getEmail());
        document.append("password", authCredential.getPassword());
        return document;
    }
}
