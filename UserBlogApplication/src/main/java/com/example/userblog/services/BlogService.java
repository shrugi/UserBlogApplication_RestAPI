package com.example.userblog.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.userblog.entities.Blog;
import com.example.userblog.entities.User;
import com.example.userblog.repos.BlogRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BlogService {

	@Autowired
	BlogRepo blogRepo;

	@Autowired
	UserService userService;

	@Value("${blog.image.upload-dir}")
	private String uploadDir;

	public List<Blog> getUserBlogs(int user_id) {
//		userService.getUser(user_id);
//		return blogRepo.findAll();

		User user = userService.getUser(user_id);
		return user.getBlogs();
	}

	public Blog createBlog(int user_id, MultipartFile multipartFile, String blogJson) throws IOException {
		Blog blog = blogJasonToBlog(blogJson);

		// convert the incoming json string to a blog object

		if (!multipartFile.isEmpty()) {
			String fileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
			Path filePath = Paths.get(uploadDir, fileName);
			Files.createDirectories(filePath.getParent());
			Files.write(filePath, multipartFile.getBytes());
			blog.setImagepath("/images/blogs/" + fileName);
		}

		User user = userService.getUser(user_id);
		blog.setUser(user);
		return blogRepo.save(blog);
	}

	public Blog getSingleBlog(int blog_id) {
		return blogRepo.findById(blog_id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Blog with id " + blog_id + " not found"));
	}

	public Blog getBlog(int user_id, int blog_id) {
		User user = userService.getUser(user_id);
		Optional<Blog> userblog = user.getBlogs().stream().filter((blog) -> blog.getId() == blog_id).findFirst();
		return userblog.orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "blog with id " + blog_id + "not found "));
	}

	public Blog updateBlog(int user_id, int blog_id, String jsonBlog, MultipartFile multipartFile) throws IOException {
		Blog newBlog = blogJasonToBlog(jsonBlog);
		Blog blog = getBlog(user_id, blog_id);
		// Update blog details
		blog.setTitle(newBlog.getTitle());
		blog.setDescription(newBlog.getDescription());

		// Check if there's a new image
		if (!multipartFile.isEmpty()) {
			// If a new image is uploaded, delete the old image
			String oldImagePath = blog.getImagepath();
			if (oldImagePath != null) {
				deletePhysicalFile(oldImagePath);
			}

			// Save the new image
			String newFileName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();
			Path filePath = Paths.get(uploadDir, newFileName);
			Files.createDirectories(filePath.getParent());
			Files.write(filePath, multipartFile.getBytes());
			blog.setImagepath("/images/blogs/" + newFileName);
		}
		return blogRepo.save(blog);
	}

	@Transactional
	public void deleteBlog(int user_id, int blog_id) {
		Blog blog = getBlog(user_id, blog_id);
		User user = userService.getUser(user_id);

		// Delete the image file associated with the blog
		String path = blog.getImagepath();
		deletePhysicalFile(path);

		user.getBlogs().remove(blog);
		userService.updateUser(user_id, user);
		blogRepo.deleteById(blog_id);
	}

	private void deletePhysicalFile(String path) {
		File file = new File("src/main/resources/static" + path);
		if (file.exists()) {
			if (file.delete()) {
				System.out.println("File deleted successfully: " + path);
			} else {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete file: " + path);
			}
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + path);
		}
	}

	public Blog blogJasonToBlog(String blogJson) throws JsonMappingException, JsonProcessingException {
		// Convert the incoming JSON string to a Blog object
		ObjectMapper objectMapper = new ObjectMapper();
		Blog blog = objectMapper.readValue(blogJson, Blog.class);
		return blog;
	}
}
