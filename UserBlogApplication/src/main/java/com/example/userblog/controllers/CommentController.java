package com.example.userblog.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userblog.entities.Comment;
import com.example.userblog.entities.User;
import com.example.userblog.services.CommentService;
import com.example.userblog.services.UserService;

@RestController
@RequestMapping("/comments")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	UserService userService;	
	
	@PostMapping("/{blog_id}")
	public ResponseEntity<Comment> createComment( @PathVariable int blog_id,
			@RequestBody Comment comment) {
		User user = userService.getCurrentUser();
		return new ResponseEntity<Comment>(commentService.createComment(comment, user.getId(), blog_id), HttpStatus.CREATED);

	}

	@GetMapping("/{blog_id}")
	public ResponseEntity<Collection<Comment>> getBlogComments(@PathVariable int blog_id) {
		return new ResponseEntity<Collection<Comment>>(commentService.getComments(blog_id), HttpStatus.OK);

	}

}
