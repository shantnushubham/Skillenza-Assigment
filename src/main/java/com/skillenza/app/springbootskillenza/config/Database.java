package com.skillenza.app.springbootskillenza.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class Database {

    @Autowired
    private Environment environment;

    public @Bean
    MongoClient mongoClient() {
        return MongoClients.create(environment.getProperty("DBClient"));
    }

    public @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), environment.getProperty("DBName"));
    }

}
