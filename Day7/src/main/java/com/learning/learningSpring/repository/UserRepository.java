package com.learning.learningSpring.repository;

import org.springframework.data.repository.CrudRepository;

import com.learning.learningSpring.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
