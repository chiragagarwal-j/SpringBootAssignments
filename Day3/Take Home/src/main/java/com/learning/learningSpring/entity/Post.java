package com.learning.learningSpring.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String content;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "author_id", referencedColumnName = "id")
	private User author;
}
