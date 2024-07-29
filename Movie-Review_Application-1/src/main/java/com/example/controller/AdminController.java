package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.MovieCreateRequest;
import com.example.models.Movie;
import com.example.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/test")
	public String test() {
		return "radhe radhe";
	}
	
	@PostMapping("/add")
	public Movie addMovie(@RequestBody MovieCreateRequest movieCreateRequest) {
		return adminService.addMovie(movieCreateRequest);
	}

}
