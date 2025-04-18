package com.shamir.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	private static final String USER = "USER";
	private static final String ADMIN = "ADMIN";
	
	@SuppressWarnings("deprecation")
	@Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	// Temporary workaround for development
    	/*http
    			.authorizeRequests()
    			.mvcMatchers("/**").permitAll();
    	
        return http.build();
        */
    	
        http
        	//.requiresChannel()
        	//.anyRequest()
        	//.requiresSecure() // Forces all requests to use HTTPS
        	//.and() 
	        .authorizeRequests()
	        .mvcMatchers("/").hasAnyRole(USER)
	        .mvcMatchers("/register/**").permitAll()
	        .mvcMatchers("/user/**").hasRole(USER)
	        .mvcMatchers("/browse/**").hasRole(USER)
	        .mvcMatchers("/book/create/**").hasRole(USER)
	        .anyRequest().authenticated()
		        .and().formLogin()
		    	.loginPage("/login")
		    	.usernameParameter("username")
	    	.permitAll()
	    	.and()
	        .httpBasic()
	        .and()
	        .logout()
	        .logoutUrl("/logout")
	        .logoutSuccessUrl("/login");
        

        
        return http.build();

    }	
    
}
