package com.shamir.bookstore.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BookTagKey implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "tag_id")
	private Long tagId;

	@Column(name = "book_id")
	private Long bookId;

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

}
