package com.example.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.example.models.Transaction;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionCreateRequest {

	@NotBlank
	private String sender;
	
	@NotBlank
	private String receiver;
	
	@Positive
	private long amount;
	
	private String purpose;
	
	public Transaction to() {
		return Transaction.builder()
				.sender(sender)
				.receiver(receiver)
				.amount(amount)
				.purpose(purpose)
				.build();
	}
}
