package com.shamir.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shamir.bookstore.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public User findByUserId(Long userId);
	public User findByUsername(String username);
}
