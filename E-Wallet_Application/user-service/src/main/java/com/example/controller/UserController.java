package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserCreateRequest;
import com.example.model.User;
import com.example.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("")
	public User createUser(@Valid @RequestBody UserCreateRequest userCreateRequest){
		return userService.create(userCreateRequest);
	}
	
	/**
	 * Retrieval
	 * 1. Cache - if present return from here
	 * 2. DB - fetch from DB and then put it in the cache for subsequent retrievals
	 * 
	 * Feature requirement : whenever an account is created, we will add  Rs 100 in the wallet (create a wallet and then add cash. )
	 */
	
	@GetMapping("")
	public User getUser(@RequestParam("id") Integer userId){
		return userService.get(userId);
//		System.out.println(userId);
//		return null;
	}
	
}



// APIs should not be same.
// Method + Path
