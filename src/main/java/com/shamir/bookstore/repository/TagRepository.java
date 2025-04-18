package com.shamir.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shamir.bookstore.model.Tag;

public interface TagRepository  extends JpaRepository<Tag, Long>{

}
