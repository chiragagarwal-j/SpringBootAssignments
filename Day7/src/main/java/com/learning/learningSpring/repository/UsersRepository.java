package com.learning.learningSpring.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.learning.learningSpring.entity.Users;

public interface UsersRepository extends CrudRepository<Users, Long>{
    public Optional<Users> findByName(String name);
}