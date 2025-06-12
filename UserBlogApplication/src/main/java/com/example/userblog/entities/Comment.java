package com.example.userblog.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int commentId;
	String commentText;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	User user;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "blog_id")
	Blog blog;

}
