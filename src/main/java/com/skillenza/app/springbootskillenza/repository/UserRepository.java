package com.skillenza.app.springbootskillenza.repository;

import com.skillenza.app.springbootskillenza.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query(value = " {'email' : '?0'} ")
    User findUserByEmail(String email);
}
