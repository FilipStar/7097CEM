package com.shamir.bookstore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shamir.bookstore.mapper.UserRowMapper;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		logger.info("LoadUserByUsername Start: " + username);
		
		String userQuery = "SELECT username, password, role FROM user WHERE username = ?";
		
		// Spring Security User object
		User user = jdbcTemplate.queryForObject(userQuery, new UserRowMapper(), username);
		logger.info("LoadUserByUsername End");
		
		return user;
		/*
		com.example.prowork.model.User user = userRepository.findByEmail(email);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true, true, true,
                List.of(new SimpleGrantedAuthority(user.getRole()))
        );
		*/
	}
	
}
