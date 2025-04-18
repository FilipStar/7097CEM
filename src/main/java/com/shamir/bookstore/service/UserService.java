package com.shamir.bookstore.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shamir.bookstore.model.Book;
import com.shamir.bookstore.model.Receipt;
import com.shamir.bookstore.model.User;
import com.shamir.bookstore.repository.BookRepository;
import com.shamir.bookstore.repository.ReceiptRepository;
import com.shamir.bookstore.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository UserRepository;
	@Autowired 
	private BookRepository bookRepository;
	@Autowired
	private ReceiptRepository receiptRepository;
	
	public List<User> findAll() {
        return UserRepository.findAll();
    }
    
    public User findByUserId(Long userId) {
    	return UserRepository.findByUserId(userId);
    }
    
    public User findByEmail(String email) {
    	return UserRepository.findByUsername(email);
    }

    
    public Long save(User user) {
    	User aUser = UserRepository.save(user);
    	return aUser.getUserId();
    }
        
    public void delete(User user) {
    	
    	for (Book book : user.getBooks()) {
    		bookRepository.delete(book);
    	}
    	
    	for (Receipt receipt : user.getReceipts()) {
    		receiptRepository.delete(receipt);
    	}
    	
    	UserRepository.delete(user); 
    }
    
    public long count() {
    	return UserRepository.count();
    }
}
