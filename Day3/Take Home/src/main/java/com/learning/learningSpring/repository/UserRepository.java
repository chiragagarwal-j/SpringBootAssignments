package com.learning.learningSpring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.learning.learningSpring.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
