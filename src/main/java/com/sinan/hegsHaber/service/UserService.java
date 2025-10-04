package com.sinan.hegsHaber.service;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinan.hegsHaber.entity.User;
import com.sinan.hegsHaber.repository.UserRepository;
import com.sinan.hegsHaber.dto.UserDto;
import com.sinan.hegsHaber.mapper.UserMapper;

import lombok.Data;

@Service
@Data
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDto getUserById(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? userMapper.toUserDTO(user) : null;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserDTO).toList();
    }

    public ResponseEntity<List<UserDto>> searchUsersByName(String username) {
        List<User> users = userRepository.findByUsernameContainingIgnoreCase(username);
        List<UserDto> userDtos = users.stream().map(userMapper::toUserDTO).toList();
        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }

}
