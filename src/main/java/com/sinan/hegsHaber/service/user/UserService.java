package com.sinan.hegsHaber.service.user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinan.hegsHaber.dto.auth.UserDto;
import com.sinan.hegsHaber.entity.user.User;
import com.sinan.hegsHaber.mapper.UserMapper;
import com.sinan.hegsHaber.repository.user.UserRepository;

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

    public List<UserDto> searchUsersByName(String username) {
        List<User> users = userRepository.findByUsernameContainingIgnoreCase(username);
        return users.stream().map(userMapper::toUserDTO).toList();// kullanıcı bulunamazsa boş liste döner
    }

    public long getUserCount() {
        return userRepository.count();
    }
}
