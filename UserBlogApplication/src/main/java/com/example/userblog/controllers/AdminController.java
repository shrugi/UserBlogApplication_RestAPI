package com.example.userblog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.userblog.entities.User;
import com.example.userblog.services.UserService;


@RestController
@RequestMapping("/admin")
public class AdminController {

	
	@Autowired
	UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<Page<User>> getAllUsers(@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size,
	        @RequestParam(defaultValue = "id") String sortBy) {

	    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
	    Page<User> result = (Page<User>) userService.getUsers(pageable);
	    return new ResponseEntity<Page<User>>(result, HttpStatus.OK); // ✅ Explicit generic type
	}


	
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<HttpStatus> deleteuserByIsd(@PathVariable int id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	

	@GetMapping("/users/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
		return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
	}
}
