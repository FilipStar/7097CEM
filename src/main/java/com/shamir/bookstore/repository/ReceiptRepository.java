package com.shamir.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shamir.bookstore.model.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Long>{
	
	public Receipt findByReceiptId(Long receiptId);
}
