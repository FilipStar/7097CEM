package com.shamir.bookstore.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

// Spring Security User Object
public class UserRowMapper implements RowMapper<User>{
	
	Logger logger = LoggerFactory.getLogger(UserRowMapper.class);
	
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		logger.info("UserRowMapper ResultSet: " + rs.getString("password"));
		
		String username = rs.getString("username");
		String password = rs.getString("password");
		String role = rs.getString("role");
				
		List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
		
		return new User(username, password, true, true, true, true, authorities);
 
	}
	
}

