package com.simplyfly.service;

import com.simplyfly.dto.UserDTO;
import com.simplyfly.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Integer userId);
    List<UserDTO> getAllUsers();
    User updateUser(Integer userId, User user);
    void deleteUser(Integer userId);
    User authenticateUser(String email, String password);
}
