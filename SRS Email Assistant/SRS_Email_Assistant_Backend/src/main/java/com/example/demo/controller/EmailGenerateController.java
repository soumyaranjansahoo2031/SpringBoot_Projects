package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.EmailRequest;
import com.example.demo.service.EmailGenerateService;

import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")  // Allow React frontend
@RestController
@RequestMapping("/api/email")
public class EmailGenerateController {
	
	private final EmailGenerateService emailGenerateService;
	
	public EmailGenerateController(EmailGenerateService emailGeneratorService) {
		this.emailGenerateService = emailGeneratorService;
	}

	@PostMapping("/generate")
	public Mono<ResponseEntity<String>> emailReplyGenerate(@RequestBody EmailRequest request) {
//		System.out.println("inside controller");
	    return emailGenerateService.generateReply(request)
	        .map(ResponseEntity::ok)
	        .defaultIfEmpty(ResponseEntity.badRequest().body("Error generating email response"));
	}
}
