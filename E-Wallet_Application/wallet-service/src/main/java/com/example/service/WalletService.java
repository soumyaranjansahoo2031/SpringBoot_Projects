package com.example.service;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.models.Wallet;
import com.example.repositories.WalletRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WalletService {
	
	@Autowired 
	JSONParser jsonParser;
	
	@Autowired
	WalletRepository walletRepository;
	
	@Autowired
	KafkaTemplate<String, String> kt;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Value("${wallet.promotional.balance}")
	private long promotionalBalance;

	private static final String USER_CREATED_TOPIC ="user_created";
	private static final String TRANSACTION_INITIATED_TOPIC ="transaction_initiated";
	private static final String WALLET_UPDATED_TOPIC = "wallet_updated";
	
	@KafkaListener(topics= {USER_CREATED_TOPIC},groupId = "group_id1")               // => creating a consumer
	public void createWallet(String msg) throws ParseException {
		
		JSONObject data = (JSONObject) jsonParser.parse(msg); 
		
		String contact = (String)data.get("contact_no");
		
		Wallet wallet = walletRepository.findByContact(contact);
		if(wallet != null) {
			return ;
		}
		wallet = Wallet.builder()
				.contact(contact)  // Set the contact field
				.balance(promotionalBalance)
				.build();
		walletRepository.save(wallet);
	}
	
	@KafkaListener(topics= {TRANSACTION_INITIATED_TOPIC},groupId = "group_id1")               // => creating a consumer
	public void updateWallet(String msg) throws ParseException, JsonProcessingException {
		
		JSONObject data = (JSONObject) jsonParser.parse(msg); 
		
		String sender = (String)data.get("sender");
		String receiver = (String)data.get("receiver");
		long amount = (long)data.get("amount");
		String externalTransactionId = (String)data.get("externalTransactionId");
		
		JSONObject message = new JSONObject();
        message.put("receiver", receiver);
        message.put("sender", sender);
        message.put("amount", amount);
        message.put("externalTransactionId", externalTransactionId);
		
		Wallet sender_Wallet = walletRepository.findByContact(sender);
		Wallet receiver_Wallet = walletRepository.findByContact(receiver);
		
		if(sender_Wallet == null || receiver_Wallet == null || sender_Wallet.getBalance() < amount) {
			message.put("walletUpdateStatus", "FAILED");
			kt.send(WALLET_UPDATED_TOPIC, objectMapper.writeValueAsString(message));
			return ;
		}
		
		try {
            walletRepository.updateWalletByContact(sender, -amount);
            walletRepository.updateWalletByContact(receiver, amount);
            message.put("walletUpdateStatus", "SUCCESS");
            kt.send(WALLET_UPDATED_TOPIC, objectMapper.writeValueAsString(message));
        }catch (Exception e){
            message.put("walletUpdateStatus", "FAILED");
            kt.send(WALLET_UPDATED_TOPIC, objectMapper.writeValueAsString(message));
        }
	}
	
}
