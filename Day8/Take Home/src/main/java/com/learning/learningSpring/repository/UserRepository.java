package com.learning.learningSpring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.learning.learningSpring.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    public Optional<User> findByName(String name);

}
