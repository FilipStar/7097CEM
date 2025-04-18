package com.shamir.bookstore.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "book")
public class Book {
	
	@Id
    @Column(name="book_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bookId;
	
	@ManyToOne
    @JoinColumn(name = "user_id")  // Foreign key in User table
	private User user;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description", length = 1024)
	private String description;
	
	@Column(name="author")
	private String author;
	
	@Column(name="translator")
	private String translator;
	
	@Column(name="format")
	private String format;
	
	@Column(name="price")
	private Long price;
	
	@OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private List<BookTag> bookTag;

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTranslator() {
		return translator;
	}

	public void setTranslator(String translator) {
		this.translator = translator;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public List<BookTag> getBookTag() {
		return bookTag;
	}

	public void setBookTag(List<BookTag> bookTag) {
		this.bookTag = bookTag;
	}
	
	
}
