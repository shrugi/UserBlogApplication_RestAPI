package com.example.userblog.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.userblog.entities.Blog;

@Repository
public interface BlogRepo extends JpaRepository<Blog, Integer> {

}
