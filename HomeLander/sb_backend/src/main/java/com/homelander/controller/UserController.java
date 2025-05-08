package com.homelander.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homelander.model.User;
import com.homelander.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
//	private final UserServiceImpl userServiceImpl;
//	public UserController(UserServiceImpl userServiceImpl) {
//        this.userService = userServiceImpl;
//    }

	
	//create a user
	@PostMapping("/register")
	public User createUser(@RequestBody User user) {
		return userService.registerUser(user);
	}
	
	//login a user
	@PostMapping("/login")
	public User loginUser(@RequestParam String email,@RequestParam String password) {
		return userService.loginUser(email,password);
	}
	
	//get all users
	@GetMapping
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	//get user by id
	@GetMapping("/{id}")
	public User getUserById(@PathVariable String id){
		return userService.getUserById(id);
	}
	
	//update user by id
	@PutMapping("/{id}")
	public User updateUserById(@PathVariable String id,@RequestBody User updatedUser) {
		return userService.updateUser(id, updatedUser);
	}
	
	//Delete a user
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
	}
	
	//update users flatId
	@PatchMapping("/{userId}/flat")
	public ResponseEntity<String> updateUserFlatId(
//	public User updateUserFlatId(
			@PathVariable String userId,
			@RequestParam(required = false) String flatId){
		userService.updateUserFlatId(userId, flatId);
		return ResponseEntity.ok("User's flatId updated successfully");
//		return userService.getUserById(userId);
	}
}
