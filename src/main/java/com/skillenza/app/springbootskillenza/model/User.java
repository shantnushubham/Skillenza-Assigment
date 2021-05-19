package com.skillenza.app.springbootskillenza.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@ToString
@Document
public class User {

    @Nullable
    @Id
    private UUID id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    public User(@Nullable UUID id, String email, String password, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {
    }
}
