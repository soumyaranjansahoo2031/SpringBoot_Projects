package com.example.service;

import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.dto.TransactionCreateRequest;
import com.example.models.Transaction;
import com.example.models.TransactionStatus;
import com.example.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TransactionService {
	
	@Autowired
	TransactionRepository txnRepository;
	
	@Autowired
	KafkaTemplate<String, String> kt;
	
	private static final String TRANSACTION_INITIATED_TOPIC = "transaction_initiated";
	private static final String WALLET_UPDATED_TOPIC = "wallet_updated";
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	JSONParser jsonParser;

	public String transact(TransactionCreateRequest txnCreateRequest) throws JsonProcessingException {
		
		Transaction transaction = txnCreateRequest.to();
		
		transaction.setExternalTransactionId(UUID.randomUUID().toString());
		transaction.setTransactionStatus(TransactionStatus.PENDING);
		
		txnRepository.save(transaction);
		
		JSONObject data = new JSONObject();
		
		data.put("sender", transaction.getSender());
		data.put("receiver", transaction.getReceiver());
		data.put("amount", transaction.getAmount());
		data.put("externalTransactionId", transaction.getExternalTransactionId());
		
		data.put("purpose", transaction.getPurpose());
		
		kt.send(TRANSACTION_INITIATED_TOPIC,objectMapper.writeValueAsString(data));
		
		return transaction.getExternalTransactionId();
	}
	
	@KafkaListener(topics= {WALLET_UPDATED_TOPIC},groupId="group_id1")
	public void updateTxn(String msg) throws ParseException {
		
		JSONObject data = (JSONObject) jsonParser.parse(msg);
		
        String walletUpdateStatus = String.valueOf(data.get("walletUpdateStatus"));
        String externalTransactionId = String.valueOf(data.get("externalTransactionId"));
        
        TransactionStatus transactionStatus = walletUpdateStatus.equals("FAILED") ? TransactionStatus.FAILED : TransactionStatus.SUCCESSFUL;

        Transaction transaction = txnRepository.findByExternalTransactionId(externalTransactionId);
        transaction.setTransactionStatus(transactionStatus);
        
        txnRepository.updateTransactionStatus(externalTransactionId, transactionStatus);

	}
}
