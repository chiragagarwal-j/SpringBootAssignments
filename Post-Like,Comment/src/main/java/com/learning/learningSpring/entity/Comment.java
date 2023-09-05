package com.learning.learningSpring.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Comment")
@Data
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "postId", referencedColumnName = "id")
    private Post post;

	private String content;

}
