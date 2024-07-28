package com.example.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.Repository.UserCacheRepository;
import com.example.Repository.UserRepository;
import com.example.dto.UserCreateRequest;
import com.example.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserCacheRepository userCacheRepository;
	
	@Autowired
	KafkaTemplate<String, String> kt;
	
	@Autowired
	ObjectMapper objectMapper;
	
	private static final String USER_CREATED_TOPIC = "user_created";
	
	public User create(UserCreateRequest userCreateRequest) {
		User user = userCreateRequest.to();
		user =  userRepository.save(user);
		//1- publish an event that the user is created

		// 2- Wallet Service's consumer will be listening to this event and
		// will create wallet for the user with the given balance
		
		// 3- Notification Service consumer will be listening to this and
		// send email for account verification.

		try {
			String data = objectMapper.writeValueAsString(user);
			kt.send(USER_CREATED_TOPIC, data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	public User get(Integer userId) {
		
		User user = userCacheRepository.get(userId);
		System.out.println(user);
		if(user == null) {
//			System.out.println("cache mein to nahin hai-- mysql mein dhundo");
			user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());
			userCacheRepository.save(user);
		}
		return user;
	}
}

