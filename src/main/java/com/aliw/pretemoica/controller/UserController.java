package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.dto.UserSummaryDto;
import com.aliw.pretemoica.repository.UserRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping
  public List<UserSummaryDto> getAllUsers() {
    return userRepository.findAll().stream()
        .map(user -> new UserSummaryDto(user.getId(), user.getEmail()))
        .toList();
  }
}
