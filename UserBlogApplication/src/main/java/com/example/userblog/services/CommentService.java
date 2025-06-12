package com.example.userblog.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.userblog.entities.Blog;
import com.example.userblog.entities.User;
import com.example.userblog.entities.Comment;
import com.example.userblog.repos.CommentRepo;

@Service
public class CommentService {

	@Autowired
	CommentRepo commentRepo;

	@Autowired
	UserService userService;

	@Autowired
	BlogService blogService;

	public Comment createComment(Comment comment, int user_id, int blog_id) {
		User user = userService.getUser(user_id);
		Blog blog = blogService.getSingleBlog(blog_id);
		comment.setUser(user);
		comment.setBlog(blog);
		return commentRepo.save(comment);
	}

	public Collection<Comment> getComments(int blog_id) {
		return commentRepo.findByBlogId(blog_id);
	}

}
