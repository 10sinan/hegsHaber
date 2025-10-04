package com.sinan.hegsHaber.service;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinan.hegsHaber.entity.User;
import com.sinan.hegsHaber.repository.UserRepository;

import lombok.Data;

@Service
@Data
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElse(null);// Kullanıcı bulunamazsa null döner
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();// Kullanıcı listesini döner
    }

    public ResponseEntity<List<User>> searchUsersByName(String name) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

}
