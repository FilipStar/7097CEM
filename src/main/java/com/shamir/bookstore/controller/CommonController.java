package com.shamir.bookstore.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shamir.bookstore.model.Book;
import com.shamir.bookstore.model.Tag;
import com.shamir.bookstore.model.User;
import com.shamir.bookstore.service.BookService;
import com.shamir.bookstore.service.FileService;
import com.shamir.bookstore.service.UserService;

import jakarta.validation.Valid;


@Controller
public class CommonController {
	
	Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private FileService fileService;
	@Autowired
	private BookService bookService;
	
    @GetMapping("/login")
    public String viewLoginPage() {     
    	logger.info("Endpoint - /login");
        return "login";
    }
    
    @RequestMapping("/register")
    public String viewRegistrationChoicePage(Model model) {
    	logger.info("Endpoint - /register");
    	
    	User user = new User();
    	user.setUserId(0L);
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
  		
  		user.setProfilePicture(file.getOriginalFilename());
  		user.setRole("ROLE_USER");
  		
  		
  		userService.save(user); //save to User table        

        return "redirect:/"; //return to index page 
    }

    @RequestMapping("/user/edit/{userId}")
    public String editEmployee(@PathVariable(name = "userId") String userIdStr,
    							Model model) {
        logger.info("Endpoint - /user/edit/" + userIdStr);
		
		User user = userService.findByUserId(Long.parseLong(userIdStr));
		model.addAttribute("user", user);

        return "registration";
    } 
	
	@RequestMapping("/user/update/{userId}")
    public String updateEmployee(@Valid @ModelAttribute("user") User user,
    							@PathVariable(name = "userId") String userIdStr,
    							Model model) {
        logger.info("Endpoint - /user/update/" + userIdStr);
		
        User aUser = userService.findByUserId(Long.parseLong(userIdStr));
        
        user.setUserId(aUser.getUserId());
        user.setProfilePicture(aUser.getProfilePicture());
        user.setPassword(aUser.getPassword());
        user.setRole("ROLE_USER");
		
		userService.save(user);

        return "redirect:/"; //return to login
    } 
	
	@RequestMapping("/user/delete/{userId}")
    public String deleteEmployee(@PathVariable(name = "userId") String userIdStr) {
        logger.info("Endpoint - /user/delete/" + userIdStr);
		
		User user = userService.findByUserId(Long.parseLong(userIdStr));
        userService.delete(user);

        return "redirect:/login"; //return to login
    } 
      
    
    @RequestMapping("/")
    public String viewHomePage(Model model) {
    	logger.debug("Endpoint: /");
    	
    	org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User user = userService.findByEmail(securityUser.getUsername());
    	
    	model.addAttribute("user", user);
    	
        return "index"; // home page
    }
    
    @RequestMapping("/book/new")
    public String viewPublishBookPage(Model model) {
    	logger.info("Endpoint - /book/new");
    	
    	org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User user = userService.findByEmail(securityUser.getUsername());
    	Set<Book> books = user.getBooks();
    	
    	Book book = new Book();
    	book.setBookId(0L);
    	
    	List<Tag> tags = bookService.findAllTags();
    	logger.info("book Size " + books.size());
    	model.addAttribute("user", user);
    	model.addAttribute("books", books);
    	model.addAttribute("book", book);
    	model.addAttribute("tags", tags);
    	
    	return "book_publisher";
	}
	
	
	@RequestMapping("/book/save")
    public String saveBook(@Valid @ModelAttribute("book") Book book,
    									Model model,
    									@RequestParam List<Long> selectedTagIds,
    									@RequestParam("file") MultipartFile file) {
    	logger.info("Endpoint - /book/save");
    	
    	org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User user = userService.findByEmail(securityUser.getUsername());
    	
    	// Save profile picture
  		try {
  			fileService.save(file);
   
  		} catch (Exception e) {
  			//message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
  			//model.addAttribute("message", message);
  		}
  		
    	book.setBookCover(file.getOriginalFilename());
    	book.setUser(user);
    	Long bookId = bookService.save(book, selectedTagIds);
    	
    	
    	return "redirect:/book/edit/" + bookId;
    	
	}
	
	@RequestMapping("/book/edit/{bookId}")
    public String editBook(@PathVariable(name = "bookId") String bookIdStr,
    									Model model) {
		logger.info("Endpoint - /emloyer/edit/" + bookIdStr);
    	
    	org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User user = userService.findByEmail(securityUser.getUsername());
    	Set<Book> books = user.getBooks();
    	
    	Book book = bookService.findByBookId(Long.parseLong(bookIdStr));
    	List<Tag> tags = bookService.findAllTags();
    	model.addAttribute("user", user);
    	model.addAttribute("books", books);
    	model.addAttribute("book", book);
    	model.addAttribute("tags", tags);
    	
    	return "book_publisher";
	}
	
	@RequestMapping("/book/delete/{bookId}")
    public String deleteBook(@PathVariable(name = "bookId") String bookIdStr) {
		logger.info("Endpoint - /emloyer/edit/" + bookIdStr);
    	
		Book book = bookService.findByBookId(Long.parseLong(bookIdStr));
		logger.info(book.getAuthor());
		bookService.delete(book);
    	
    	return "redirect:/book/new";
	}
	
	@RequestMapping("/book/browse")
	public String showBooks(Model model) {
		
		org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User user = userService.findByEmail(securityUser.getUsername());
    	List<Book> books = bookService.findAll();
    	
    	List<Tag> tags = bookService.findAllTags();
    	
    	model.addAttribute("user", user);
    	model.addAttribute("tags", tags);
    	model.addAttribute("books", books);
    	
    	return "list_books";
	}
	
	@GetMapping("/book/filter")
    public String filterBooks(@RequestParam(required = false) List<Long> tagIds, Model model) {
        
		org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User user = userService.findByEmail(securityUser.getUsername());
		List<Book> books;
		
        if (tagIds == null || tagIds.isEmpty()) {
            books = bookService.findAll(); // No filter
        } else {
            books = bookService.findDistinctByTags(tagIds);
        }
        
        List<Tag> tags = bookService.findAllTags();
        
        model.addAttribute("user", user);
        model.addAttribute("tags", tags);
        model.addAttribute("books", books);
        return "list_books";
    }
	
}
