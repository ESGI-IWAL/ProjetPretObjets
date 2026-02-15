package com.aliw.pretemoica.service;

import com.aliw.pretemoica.dto.UserDto;
import com.aliw.pretemoica.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerNewUser(UserDto dto) {

        if (!dto.getPassword().equals(dto.getMatchingPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true); // pour l'instant, sans validation mail

        userRepository.save(user);
    }
}
