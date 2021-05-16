package com.skillenza.app.springbootskillenza.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Objects;

@Configuration
public class Database {

    @Autowired
    private Environment environment;

    public @Bean
    MongoClient mongoClient() {
        return MongoClients.create(Objects.requireNonNull(environment.getProperty("DBClient")));
    }

    public @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), Objects.requireNonNull(environment.getProperty("DBName")));
    }

}
