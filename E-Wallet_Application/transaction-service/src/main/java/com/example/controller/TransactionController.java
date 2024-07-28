package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.TransactionCreateRequest;
import com.example.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class TransactionController {
	
	@GetMapping("/hello")
	public String hello() {
		return "hii";
	}
	
	@Autowired
	TransactionService txnService;

	
	@PostMapping(value="/transact")
	public String transact(@Valid @RequestBody TransactionCreateRequest txnCreateRequest) throws JsonProcessingException {
		System.out.println(" hello ");
		return txnService.transact(txnCreateRequest);
	}
	

}
