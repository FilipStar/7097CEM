package com.shamir.bookstore.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shamir.bookstore.model.Book;
import com.shamir.bookstore.model.BookTag;
import com.shamir.bookstore.model.BookTagKey;
import com.shamir.bookstore.model.Tag;
import com.shamir.bookstore.repository.BookRepository;
import com.shamir.bookstore.repository.BookTagRepository;
import com.shamir.bookstore.repository.TagRepository;

@Service
@Transactional
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private BookTagRepository bookTagRepository;
	@Autowired
	private TagRepository tagRepository;

	
	public List<Book> findAll() {
        return bookRepository.findAll();
    }
    
	public List<Tag> findAllTags() {
		return tagRepository.findAll();
	}
	
    public Book findByBookId(Long bookId) {
    	return bookRepository.findByBookId(bookId);
    }
    
    public Tag findByTagId(Long tagId) {
    	return tagRepository.findByTagId(tagId);
    }
    
    public List<Book> findDistinctByTags(List<Long> tags) {
    	
    	List<BookTag> bookTags = bookTagRepository.findAll();
    	List<Book> books = new ArrayList<Book>();
    	
    	for (Long tagId : tags) {
    		for (BookTag bookTag : bookTags) {
    			if (tagId == bookTag.getTag().getTagId()) {
    				books.add(bookTag.getBook());
    			}
    		}
    	}
    	return books;
    }
    
    public Long save(Book book, List<Long> tagIds) {
    	
    	Book aBook = bookRepository.save(book);
    	
    	for (Long tagId : tagIds) {
    		
    		BookTagKey bookTagKey = new BookTagKey();
    		BookTag bookTag = new BookTag();
    		
    		bookTag.setBook(book);
    		bookTag.setTag(tagRepository.findByTagId(tagId));
    		bookTag.setId(bookTagKey);
    		
    		bookTagRepository.save(bookTag);
    	}
    	
    	return aBook.getBookId();
    }
        
    public void delete(Book book) {
    	
    	for (BookTag bookTag : book.getBookTag()) {
    		bookTagRepository.delete(bookTag);
    	}
    	
    	bookRepository.delete(book); 
    }
    
    
    public long count() {
    	return bookRepository.count();
    }

}
