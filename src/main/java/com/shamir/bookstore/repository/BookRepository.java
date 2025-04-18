package com.shamir.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shamir.bookstore.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	
	public Book findByBookId(Long bookId);
}
