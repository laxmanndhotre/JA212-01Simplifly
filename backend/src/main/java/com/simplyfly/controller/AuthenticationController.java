package com.simplyfly.controller;

import com.simplyfly.entity.User;
import com.simplyfly.dto.UserDTO;
import com.simplyfly.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        logger.info("Login attempt for email: {}", userDTO.getEmail());

        User user = authenticationService.authenticateUser(userDTO.getEmail(), userDTO.getPassword());

        if (user != null) {
            UserDTO responseDTO = new UserDTO();
            responseDTO.setUser_id(user.getUserId());
            responseDTO.setUsername(user.getUsername());
            responseDTO.setEmail(user.getEmail());
            responseDTO.setRole(user.getRole().name());
            responseDTO.setPassword(null);

            logger.info("Login successful for email: {}", userDTO.getEmail());
            return ResponseEntity.ok(responseDTO);
        } else {
            logger.warn("Unauthorized access attempt for email: {}", userDTO.getEmail());
            return ResponseEntity.status(403).body("Unauthorized access.");
        }
    }
}
