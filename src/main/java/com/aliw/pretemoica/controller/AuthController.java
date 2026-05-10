package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.dto.LoginRequest;
import com.aliw.pretemoica.dto.LoginResponse;
import com.aliw.pretemoica.dto.UserDto;
import com.aliw.pretemoica.service.JwtService;
import com.aliw.pretemoica.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;


    public AuthController(
            AuthenticationManager authenticationManager,
            UserService userService,
            JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<@NonNull String> register(@Valid @RequestBody UserDto dto) {
        userService.registerNewUser(dto);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<@NonNull LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            String username = auth.getName();
            String token = jwtService.generateToken(username);
            List<String> roles = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
            return ResponseEntity.ok(
                    new LoginResponse(token, "Bearer", username, roles));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).build();
        }
    }
}