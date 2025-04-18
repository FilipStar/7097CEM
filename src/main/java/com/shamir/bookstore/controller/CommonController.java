package com.shamir.bookstore.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.prowork.model.Employee;
import com.shamir.bookstore.model.User;
import com.shamir.bookstore.service.UserService;

import jakarta.validation.Valid;


@Controller
public class CommonController {
	
	Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private UserService userService;
	
    @GetMapping("/login")
    public String viewLoginPage() {     
    	logger.info("Endpoint - /login");
        return "login";
    }
    
    @RequestMapping("/register")
    public String viewRegistrationChoicePage(Model model) {
    	logger.info("Endpoint - /register");
    	
    	User user = new User();
    	model.addAttribute("user", user);
    	
        return "registration";
    }  
    
    
    @RequestMapping(value = "/register/save", method = RequestMethod.POST) 
    public String saveStudent(@Valid @ModelAttribute("user") User user,
    							BindingResult result,
    							Model model,
    							@RequestParam("file") MultipartFile file){     
    	logger.info("Endpoint - /register/save");
    	
    	// Check if User with the same email already exists
        for (User aUser: userService.findAll()) {
        	if (aUser.getEmail().equals(user.getEmail())) {
        		result.addError(new FieldError("user", "userEmail.duplicate", "User with that email already exists."));
        	}
        }
    	
    	if (result.hasErrors()) {

    	    model.addAttribute("user", user);
            return "registration"; // Reload the form with error messages
        }
    	
    	// Save profile picture
  		try {
  			fileService.save(file);
   
  		} catch (Exception e) {
  			//message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
  			//model.addAttribute("message", message);
  		}
  		
  		//String encodedPassword = new BCryptPasswordEncoder().encode(student.getPassword());
  		//student.setPassword(encodedPassword);
  		
  		employee.setProfilePicture(file.getOriginalFilename());
  		employee.setEnabled(true);
  		employee.setRole("ROLE_USER");
  		
  		
        employeeService.save(employee); //save to User table        

        return "redirect:/"; //return to index page 
    }


      
    
    @RequestMapping("/")
    public String viewHomePage(Model model) {
    	logger.debug("Endpoint: /");
    	
    	org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User user;
    	
    	// Get the users authorities (roles)
        Collection<GrantedAuthority> authorities = securityUser.getAuthorities();

        // Check if user has a specific role
        boolean isEmployee = authorities.stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_USER"));
        
        if (isEmployee) {
        	user = employeeService.findByEmail(securityUser.getUsername());
        } else {
        	user = employerService.findByEmail(securityUser.getUsername());
        }
        logger.info("IsEmployee: " + isEmployee);
        logger.info("User Type: " + user.getDtype());
        model.addAttribute("user", user);

        
    	if (user != null) {
    		logger.info("AppController::viewHomePage " + user.getClass().getName());
    	} else {
    		logger.info("AppController::viewHomePage - No User");
    	}
    	
        return "index"; // home page
    }
}
