package com.shamir.bookstore.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
public class Tag {
	
	@Id
    @Column(name="tag_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long tagId;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(mappedBy = "tag", fetch = FetchType.EAGER)
    private List<BookTag> bookTag;

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BookTag> getBookTag() {
		return bookTag;
	}

	public void setBookTag(List<BookTag> bookTag) {
		this.bookTag = bookTag;
	}
	
}

