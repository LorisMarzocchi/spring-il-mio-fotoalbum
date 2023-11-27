package com.experis.springilmiofotoalbum.service;

import com.experis.springilmiofotoalbum.model.User;
import com.experis.springilmiofotoalbum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
