package com.aliw.pretemoica.service;

import com.aliw.pretemoica.dto.auth.AuthResponse;
import com.aliw.pretemoica.dto.auth.LoginRequest;
import com.aliw.pretemoica.dto.auth.RegisterRequest;
import com.aliw.pretemoica.entity.UserEntity;
import com.aliw.pretemoica.repository.UserRepository;
import com.aliw.pretemoica.security.JwtUtil;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  @Autowired private UserRepository userRepository;

  @Autowired private JwtUtil jwtUtil;

  @Autowired private PasswordEncoder passwordEncoder;

  /**
   * Register a new user
   *
   * @param registerRequest the registration request
   * @return AuthResponse with token and user info
   * @throws IllegalArgumentException if email already exists
   */
  public AuthResponse register(RegisterRequest registerRequest) {
    // Vérifier que l'email n'existe pas déjà
    if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
      throw new IllegalArgumentException("L'email est déjà utilisé");
    }

    // Créer un nouvel utilisateur
    UserEntity newUser = new UserEntity();
    newUser.setUsername(registerRequest.getUsername());
    newUser.setEmail(registerRequest.getEmail());
    newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

    // Sauvegarder l'utilisateur
    UserEntity savedUser = userRepository.save(newUser);

    // Générer le token
    String token =
        jwtUtil.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getUsername());

    // Retourner la réponse
    return new AuthResponse(
        token, savedUser.getId(), savedUser.getEmail(), savedUser.getUsername());
  }

  /**
   * Login a user
   *
   * @param loginRequest the login request
   * @return AuthResponse with token and user info
   * @throws IllegalArgumentException if credentials are invalid
   */
  public AuthResponse login(LoginRequest loginRequest) {
    Optional<UserEntity> userOptional = userRepository.findByEmail(loginRequest.getEmail());

    if (userOptional.isEmpty()) {
      throw new IllegalArgumentException("Email ou mot de passe incorrect");
    }

    UserEntity user = userOptional.get();

    // Vérifier le mot de passe
    if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      throw new IllegalArgumentException("Email ou mot de passe incorrect");
    }

    // Générer le token
    String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getUsername());

    // Retourner la réponse
    return new AuthResponse(token, user.getId(), user.getEmail(), user.getUsername());
  }
}
