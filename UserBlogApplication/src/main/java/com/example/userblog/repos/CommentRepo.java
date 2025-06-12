package com.example.userblog.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.userblog.entities.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer>{
List <Comment>findByBlogId(int blogId);
}
