package com.homelander.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homelander.model.User;
import com.homelander.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	public User registerUser(User user) {
		if(userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new RuntimeException("Email Already exists");
		}
		return userRepository.save(user);
	}
	
	public User loginUser(String email,String Password) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(()->
				new RuntimeException("Invalid email or password"));
		if(!user.getPassword().equals(Password)) {
			throw new RuntimeException("Invalid email or password");
		}
		return user;
	}
	
	public User getUserById(String userId) {
		return userRepository.findById(userId)
				.orElseThrow(()-> new RuntimeException("User not found"));
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public User updateUser(String id,User updatedData) {
		User user = getUserById(id);
		user.setName(updatedData.getName());
		// email update pending
		user.setEmail(updatedData.getEmail());
		user.setPassword(updatedData.getPassword());
		user.setPhone(updatedData.getPhone());
		return userRepository.save(user);
	}
	
	public void deleteUser(String id) {
		userRepository.deleteById(id);
	}
	
	public void updateUserFlatId(String userId,String flatId) {
		User user = userRepository.findById(userId)
				.orElseThrow(()->new RuntimeException("User nahin mila"));
//		System.out.println(user.getFlatId());
//		System.out.println(user);
		user.setFlatId(flatId);
//		System.out.println(user.getFlatId());
		userRepository.save(user);
//		return user;
	}
}

