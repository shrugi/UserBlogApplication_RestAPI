package com.example.userblog.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "blogs")
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String description;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createDate;

	@org.springframework.data.annotation.LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime LastModifiedDate;

	
	private String imagepath;
	
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	User user;
}
