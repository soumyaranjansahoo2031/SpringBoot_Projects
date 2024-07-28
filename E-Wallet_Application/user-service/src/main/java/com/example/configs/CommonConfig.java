package com.example.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class CommonConfig {

	@Bean
	ObjectMapper getMapper() {
		return new ObjectMapper();
	}
}
