package com.example.pmtool.services;

import com.example.pmtool.domain.User;
import com.example.pmtool.exceptions.UsernameAlreadyExistException;
import com.example.pmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User user) {
        try {
            user.setUsername(user.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setConfirmPassword("");
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UsernameAlreadyExistException("Username '" + user.getUsername() + "' already exists");
        }

    }
}
