package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.entity.UserEntity;
import com.aliw.pretemoica.repository.UserRepository;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping
  public List<UserEntity> getAllUsers() {
    return userRepository.findAll();
  }

  @PostMapping
  public UserEntity createUser(@RequestBody UserEntity user) {
    return userRepository.save(user);
  }

  // Ajoute d’autres endpoints selon besoin
}
