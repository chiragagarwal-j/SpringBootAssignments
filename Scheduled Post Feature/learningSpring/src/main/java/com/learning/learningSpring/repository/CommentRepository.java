package com.learning.learningSpring.repository;

import com.learning.learningSpring.entity.Comment;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
	@Query(value = "select * from comment c where post_id = ?1", nativeQuery= true)
	List<Comment> findAllByPostId(Integer postId);

}
