package com.example.authserver.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.authserver.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	
}
