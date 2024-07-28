package com.example.config;

import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class CommonConfig {
	
	@Bean
	public ObjectMapper getMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	public JSONParser getParser() {
		return new JSONParser();
	}
}
