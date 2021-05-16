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
public class AuthCredential {

    @Nullable
    @Id
    private UUID id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    public AuthCredential(@Nullable UUID id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public AuthCredential() {
    }
}
