   package com.example.userblog.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.userblog.dtos.UserRegisterDto;
import com.example.userblog.entities.Blog;
import com.example.userblog.entities.Comment;
import com.example.userblog.entities.User;
import com.example.userblog.services.BlogService;
import com.example.userblog.services.CommentService;
import com.example.userblog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserControllers {
	@Autowired
	UserService userService;

	@GetMapping("/self")
	public ResponseEntity<User> getUserById() {
		User user = userService.getCurrentUser();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping("/self")
	public ResponseEntity<User> updateUserById(@Valid @RequestBody User user) {
		User curuser = userService.getCurrentUser();
		return new ResponseEntity<>(userService.updateUser(curuser.getId(), user), HttpStatus.OK);
	}

}
