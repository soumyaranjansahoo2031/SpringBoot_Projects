package com.homelander;

import com.homelander.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.homelander.model.User;

@SpringBootApplication
public class HomelanderBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomelanderBackendApplication.class, args);
	}
	
//	@Bean
//    CommandLineRunner runner(UserRepository userRepository) {
//        return args -> {
//            User user = new User(null, "John Doe", "john@example.com");
//            userRepository.save(user);
//            System.out.println("✔ User saved to MongoDB!");
//        };
//    }
}
