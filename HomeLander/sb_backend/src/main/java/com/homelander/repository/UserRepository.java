package com.homelander.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.homelander.model.User;

public interface UserRepository extends MongoRepository<User,String>{
	// This is a MongoDB repository for the User model, and its ID is of type String.
	// MongoRepository is a special Spring interface that gives you tons of built-in database methods 
	// like .save(), .findAll(), .findById(), .deleteById() etc.
	Optional<User> findByEmail(String email);

}
