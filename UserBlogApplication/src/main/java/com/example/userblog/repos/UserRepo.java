package com.example.userblog.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.userblog.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);
}
