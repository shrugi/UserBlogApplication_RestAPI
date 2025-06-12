package com.example.userblog.services;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.userblog.dtos.UserRegisterDto;
import com.example.userblog.entities.Role;
import com.example.userblog.entities.User;
import com.example.userblog.repos.RoleRepo;
import com.example.userblog.repos.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RoleRepo roleRepo;

//	HashMap<Integer, User> data = new HashMap<>();
//	AtomicInteger atomicInteger = new AtomicInteger(2);
//
//	UserService() {
//		data.put(1, new User(1, "Shruti", "Shrutiagre24@gmail.com", "2428"));
//		data.put(2, new User(2, "Tanvi", "Tanvi05@gmail.com", "0505"));
//	}

	@Transactional
	public User createUser(UserRegisterDto userRegisterObj) {
//		user.setId(atomicInteger.incrementAndGet());
//		return this.data.put(user.getId(), user);
		Optional<User> existingUser = userRepo.findByEmail(userRegisterObj.getEmail());
		if (existingUser.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User user already exist");
		}
		if (userRegisterObj.getRoles() == null || userRegisterObj.getRoles().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User must have atleat one role");
		}
		Set<Role> managedRoles = getManagedRoles(userRegisterObj.getRoles());
		User user = new User();
		user.setUsername(userRegisterObj.getUsername());
		user.setEmail(userRegisterObj.getEmail());
		user.setPassword(passwordEncoder.encode(userRegisterObj.getPassword()));
		user.setRoles(managedRoles);
		return this.userRepo.save(user);
	}

	// helper method to fetch and managed roles from the database

	private Set<Role> getManagedRoles(Set<Role> roles) {
		Set<Role> managedRoles = new HashSet<>();
		for (Role role : roles) {
			Role managedRole = roleRepo.findByName(role.getName()).orElseThrow(
					() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role '" + role.getName() + "' not found"));
			managedRoles.add(managedRole);

		}
		return managedRoles;
	}

//	public Collection<User> getUsers() {
////		return this.data.values();
//		return this.userRepo.findAll();
//	}

	public Page<User> getUsers(Pageable pageable) {
		return this.userRepo.findAll(pageable);
	}

	public User getUser(int id) {
//		return this.data.get(id);
		return this.userRepo.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + id + " not found"));
	}

	public User updateUser(int id, User user) {
		user.setId(id);
//		return this.data.put(user.getId(), user);
		return this.userRepo.save(user);
	}

	public void deleteUser(int id) {
//		this.data.remove(id);
		getUser(id);
		this.userRepo.deleteById(id);
	}

	public User getUserByEmail(String email) {
		return this.userRepo.findByEmail(email).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " + email + " not found"));

	}

	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal=authentication.getPrincipal()	;
		String email=((UserDetails)principal).getUsername()	;
		User user=getUserByEmail(email);
		return user;
	}

}
