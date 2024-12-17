package com.simplyfly.service.impl;

import com.simplyfly.dto.UserDTO;
import com.simplyfly.entity.User;
import com.simplyfly.exception.ResourceNotFoundException;
import com.simplyfly.exception.DuplicateResourceException;
import com.simplyfly.repository.UserRepository;
import com.simplyfly.service.AuthenticationService;
import com.simplyfly.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public User createUser(User user) {
        logger.info("Attempting to create a new user with email: {}", user.getEmail());

        if (userRepository.existsByEmail(user.getEmail())) {
            logger.error("User creation failed. Email {} is already registered.", user.getEmail());
            throw new DuplicateResourceException("Email " + user.getEmail() + " is already registered.");
        }

        User savedUser = userRepository.save(user);
        logger.info("User created successfully with ID: {}", savedUser.getUserId());
        return savedUser;
    }

    @Override
    public User getUserById(Integer userId) {
        logger.debug("Fetching user with ID: {}", userId);

        return userRepository.findById(userId).orElseThrow(() -> {
            logger.warn("User with ID {} not found.", userId);
            return new ResourceNotFoundException("User with ID " + userId + " not found.");
        });
    }

    @Override
    public List<UserDTO> getAllUsers() {
        logger.debug("Fetching all users.");

        List<UserDTO> users = userRepository.findAll().stream().map(user -> {
            UserDTO dto = new UserDTO();
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole().name());
            return dto;
        }).collect(Collectors.toList());

        logger.info("Successfully fetched {} users.", users.size());
        return users;
    }

    @Override
    public User updateUser(Integer userId, User updatedUser) {
        logger.info("Updating user with ID: {}", userId);

        User user = getUserById(userId); // Ensure the user exists

        if (!user.getEmail().equals(updatedUser.getEmail()) && userRepository.existsByEmail(updatedUser.getEmail())) {
            logger.error("User update failed. Email {} is already registered.", updatedUser.getEmail());
            throw new DuplicateResourceException("Email " + updatedUser.getEmail() + " is already registered.");
        }

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        User savedUser = userRepository.save(user);

        logger.info("User with ID: {} updated successfully.", userId);
        return savedUser;
    }

    @Override
    public void deleteUser(Integer userId) {
        logger.info("Attempting to delete user with ID: {}", userId);

        getUserById(userId); // Ensure the user exists
        userRepository.deleteById(userId);

        logger.info("User with ID: {} deleted successfully.", userId);
    }

    @Override
    public User authenticateUser(String email, String password) {
        logger.info("Attempting to authenticate user with email: {}", email);

        User authenticatedUser = authenticationService.authenticateUser(email, password);
        logger.info("User with email: {} authenticated successfully.", email);

        return authenticatedUser;
    }
}
