package com.example.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.models.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Integer>{
	
	Wallet findByContact(String contact);
	
	@Modifying
	@Transactional
	@Query("update Wallet w set w.balance = w.balance + :amount where w.contact = :contact")
	void updateWalletByContact(String contact,long amount);

}
