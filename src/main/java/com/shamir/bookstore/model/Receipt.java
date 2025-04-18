package com.shamir.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "receipt")
public class Receipt {

	@Id
    @Column(name="receipt_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long receiptId;
	
	@ManyToOne
    @JoinColumn(name = "user_id")  // Foreign key in User table
	private User user;
	
	@Column(name="total_price")
	private Long totalPrice;
	
	@Column(name="content", length = 1024)
	private String content;
}
