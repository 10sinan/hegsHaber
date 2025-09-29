package com.sinan.hegsHaber.service;

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
        return userRepository.findAll();//Kullanıcı listesini döner
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(UUID id, User user) {

        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }

    }

}
