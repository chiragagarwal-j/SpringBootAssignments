package com.learning.learningSpring.repository;

import org.springframework.data.repository.CrudRepository;

import com.learning.learningSpring.entity.LikeRecord;

public interface LikeCRUDRepository extends CrudRepository<LikeRecord, Integer> {

}
