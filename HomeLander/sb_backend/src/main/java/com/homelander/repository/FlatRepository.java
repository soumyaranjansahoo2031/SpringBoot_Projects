package com.homelander.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.homelander.model.Flat;

public interface FlatRepository extends MongoRepository<Flat,String>{
	Optional<Flat> findByJoinCode(String joinCode);
}
