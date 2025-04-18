package com.shamir.bookstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shamir.bookstore.model.Tag;
import com.shamir.bookstore.repository.TagRepository;

@SpringBootTest(classes = DemoApplication.class)
public class TagRepositoryTest {
	
	@Autowired
	private TagRepository tagRepository;
	
	//@Test
	public void addTags() {
		Tag tag1 = new Tag();
		Tag tag2 = new Tag();
		Tag tag3 = new Tag();
		Tag tag4 = new Tag();
		Tag tag5 = new Tag();
		
		tag1.setName("Romance");
		tag2.setName("Mystery");
		tag3.setName("Fantasy");
		tag4.setName("Sci-Fi");
		tag5.setName("Comedy");
		
		tagRepository.save(tag1);
		tagRepository.save(tag2);
		tagRepository.save(tag3);
		tagRepository.save(tag4);
		tagRepository.save(tag5);
	}

}
