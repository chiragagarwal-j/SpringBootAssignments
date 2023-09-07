package com.learning.learningSpring.repository;

import org.springframework.data.repository.CrudRepository;

import com.learning.learningSpring.entity.ScheduledPost;

public interface ScheduledPostRepository extends CrudRepository<ScheduledPost, Integer> {

}
