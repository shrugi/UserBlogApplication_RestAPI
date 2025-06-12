package com.example.userblog.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.userblog.entities.Blog;
import com.example.userblog.entities.User;
import com.example.userblog.services.BlogService;
import com.example.userblog.services.UserService;


@RestController
@RequestMapping("/blogs")
public class BlogController {


	@Autowired
	BlogService blogService;
	
	@Autowired
	UserService userService;
	
	
	@GetMapping("")
	public ResponseEntity<List<User>> getUserAllBlogs() {
		User user = userService.getCurrentUser();
		return new ResponseEntity(blogService.getUserBlogs(user.getId()), HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<Blog> createUserBlog(
			@RequestPart("file") MultipartFile multipartFile, @RequestParam("blog") String blogJson)
			throws IOException {
		User user = userService.getCurrentUser();
		return new ResponseEntity<>(blogService.createBlog(user.getId()	, multipartFile, blogJson), HttpStatus.CREATED);
	}

	@GetMapping("/{blog_id}")
	public ResponseEntity<Blog> getUserBlog( @PathVariable int blog_id) {
		User user = userService.getCurrentUser();
		return new ResponseEntity(blogService.getBlog(user.getId(), blog_id), HttpStatus.CREATED);
	}

	@PutMapping("/{blog_id}")
	public ResponseEntity<Blog> updateUserBlog( @PathVariable int blog_id,
			@RequestParam("blog") String blogJson, @RequestPart("file") MultipartFile multipartFile)
			throws IOException {
		User user = userService.getCurrentUser();
		return new ResponseEntity(blogService.updateBlog(user.getId(), blog_id, blogJson, multipartFile), HttpStatus.OK);
	}

	@DeleteMapping("/{blog_id}")
	public ResponseEntity deleteUser( @PathVariable int blog_id) {
		User user = userService.getCurrentUser();	
		blogService.deleteBlog(user.getId(), blog_id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
