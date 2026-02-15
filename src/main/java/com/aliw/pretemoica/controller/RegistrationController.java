package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.dto.UserDto;
import com.aliw.pretemoica.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody UserDto dto
    ) {
        userService.registerNewUser(dto);
        return ResponseEntity.ok("User registered");
    }
}