package com.example.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.models.Transaction;
import com.example.models.TransactionStatus;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

	Transaction findByExternalTransactionId(String externalTxnId);

	@Modifying
	@Transactional
	@Query("update Transaction t set t.transactionStatus = ?2 where t.externalTransactionId = ?1")
	void updateTransactionStatus(String externalTxnId, TransactionStatus transactionStatus);

}
