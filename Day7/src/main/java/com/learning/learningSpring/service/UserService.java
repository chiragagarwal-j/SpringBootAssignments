package com.learning.learningSpring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.learningSpring.entity.Users;
import com.learning.learningSpring.repository.UsersRepository;

@Service
public class UserService {

    @Autowired
    private UsersRepository usersRepository;

    public Optional<Users> authenticate(String username, String password) {
        Optional<Users> optUser = usersRepository.findByName(username);
        if (!optUser.get().getPassword().equals(password)) {
            return Optional.empty();
        }
        return optUser;
    }

    public Users create(Users users) {
        return usersRepository.save(users);
    }

}