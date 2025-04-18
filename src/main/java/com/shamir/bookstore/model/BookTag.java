package com.shamir.bookstore.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;


@Entity
public class BookTag {
	
	@EmbeddedId
    private BookTagKey id;
	
	@ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;
	
	@ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

	public BookTagKey getId() {
		return id;
	}

	public void setId(BookTagKey id) {
		this.id = id;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
}
