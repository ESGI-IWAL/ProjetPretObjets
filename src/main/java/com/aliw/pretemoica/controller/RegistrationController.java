package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.dto.UserDto;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody UserRegistrationDto dto
    ) {
        userService.registerNewUser(dto);
        return ResponseEntity.ok("User registered");
    }
}