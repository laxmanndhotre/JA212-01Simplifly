package com.simplyfly.service;

import com.simplyfly.entity.User;
import com.simplyfly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Authenticate a user by email or username and password.
     *
     * @param identifier The email or username of the user.
     * @param password   The password of the user.
     * @return The authenticated User.
     * @throws IllegalArgumentException if authentication fails.
     */
    public User authenticateUser(String identifier, String password) {
        Optional<User> optionalUser;

        // Try to fetch user by email first
        optionalUser = userRepository.findByEmail(identifier);
        if (optionalUser.isEmpty()) {
            // If not found by email, try finding by username
            optionalUser = userRepository.findByUsername(identifier);
        }

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        User user = optionalUser.get();

        // Validate password
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        return user;
    }
}
