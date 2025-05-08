package com.homelander.service;

import java.util.List;

import com.homelander.model.User;

public interface UserService {
	User registerUser(User user);
	
	User loginUser(String email,String password);
	
	User getUserById(String userId);
	
	List<User> getAllUsers();
	
	User updateUser(String id,User updatedData);
	
	void deleteUser(String id);
	
	void updateUserFlatId(String userId, String flatId);
}
