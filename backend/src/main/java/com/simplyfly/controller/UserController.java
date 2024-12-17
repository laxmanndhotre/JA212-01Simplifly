package com.simplyfly.controller;

import com.simplyfly.dto.UserDTO;
import com.simplyfly.entity.User;
import com.simplyfly.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("Creating user: {}", user);
        User createdUser = userService.createUser(user);
        logger.info("User created successfully: {}", createdUser);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        logger.info("Fetching user with ID: {}", id);
        User user = userService.getUserById(id);
        logger.info("Fetched user details: {}", user);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("Fetching all users");
        List<UserDTO> users = userService.getAllUsers();
        logger.info("Fetched {} users", users.size());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        logger.info("Updating user with ID: {}", id);
        User updatedUser = userService.updateUser(id, user);
        logger.info("User updated successfully: {}", updatedUser);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        logger.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        logger.info("User with ID: {} deleted successfully", id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
