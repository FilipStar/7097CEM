package com.shamir.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shamir.bookstore.model.BookTag;

public interface BookTagRepository extends JpaRepository<BookTag, Long>{

}
