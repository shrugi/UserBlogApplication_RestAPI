package com.example.userblog.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@NoArgsConstructor
//@AllArgsConstructor
//@Setter
//@Getter
//@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

//	@Nonnull
	@NotBlank	
	@Size(min = 4, max = 30, message = "username should be between 4-30 characters")
	private String username;

	@Email(message = "Invalid email please enter valid email")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
	@NotBlank
	private String email;

//	@Size(min = 6, max = 10, message = "Password should be between 6-10 characters")
//	@NotBlank
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	@JsonIgnore
	private LocalDateTime createDate;

	@org.springframework.data.annotation.LastModifiedDate
	@Column(nullable = false)
	@JsonIgnore
	private LocalDateTime LastModifiedDate;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Blog> blogs;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user-id"), inverseJoinColumns = @JoinColumn(name = "role-id"))
	private Set<Role> roles = new HashSet<>();
}
