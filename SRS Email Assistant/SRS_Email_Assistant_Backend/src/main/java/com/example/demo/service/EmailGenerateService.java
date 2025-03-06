package com.example.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.entity.EmailRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Service
public class EmailGenerateService {
	
	private final WebClient webClient;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Value("${gemini.api.url}")
	private String geminiApiUrl;
	
	@Value("${gemini.api.key}")
	private String geminiApiKey;

	public EmailGenerateService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.build();
	}

	public Mono<String> generateReply(EmailRequest emailRequest) {
		
		// Build the prompt
//		System.out.println("building the prompt");
		String prompt = "Generate email reply based on: " + emailRequest.getEmailContent() 
                        +"\n With tone: " + emailRequest.getTone() 
                        +"\n With proper mail formatting";
		
		
		// Craft the Request 
//		System.out.println("crafting the prompt");
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                       Map.of("parts", new Object[]{
                               Map.of("text", prompt)
                       })
                }
        );
        
		// Do the request and get the response (Make an asynchronous call without blocking )
//        System.out.println("request and response --> gemini rocks");
		return webClient.post()
				.uri(geminiApiUrl + geminiApiKey)
				.header("Content-Type", "application/json")
				.bodyValue(requestBody)
				.retrieve()
				.bodyToMono(String.class)
				.flatMap(this::extractResponseContent);
	}

	private Mono<String> extractResponseContent(String response) {
		try {
			JsonNode rootNode = objectMapper.readTree(response);
			String extractedText = rootNode.path("candidates")
					.get(0)
					.path("content")
					.path("parts")
					.get(0)
					.path("text")
					.asText();
			return Mono.just(extractedText);
		} catch (Exception e) {
			return Mono.error(new RuntimeException("Error processing request: " + e.getMessage()));
		}
	}
}
