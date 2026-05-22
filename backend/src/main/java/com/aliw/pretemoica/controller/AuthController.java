package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.dto.auth.AuthResponse;
import com.aliw.pretemoica.dto.auth.LoginRequest;
import com.aliw.pretemoica.dto.auth.RegisterRequest;
import com.aliw.pretemoica.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationService authenticationService;

  public AuthController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  /**
   * Register a new user
   *
   * @param registerRequest the registration request
   * @return AuthResponse with token and user info
   */
  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
    try {
      AuthResponse response = authenticationService.register(registerRequest);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } catch (IllegalArgumentException iae) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  /**
   * Login a user
   *
   * @param loginRequest the login request
   * @return AuthResponse with token and user info
   */
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
    try {
      AuthResponse response = authenticationService.login(loginRequest);
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException iae) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
