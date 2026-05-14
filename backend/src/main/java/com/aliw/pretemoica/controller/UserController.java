package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.dto.UserDto;
import com.aliw.pretemoica.mapper.UserMapper;
import com.aliw.pretemoica.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public java.util.List<UserDto> getAllUsers() {
    return UserMapper.toDtoList(userService.getAll());
  }

  @GetMapping("/{id}")
  public UserDto getUserById(@PathVariable Long id) {
    return UserMapper.toDto(userService.getById(id));
  }

  @PostMapping
  public UserDto createUser(@RequestBody UserDto user) {
    return UserMapper.toDto(userService.create(UserMapper.toEntity(user)));
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    userService.delete(id);
  }

  // Ajoute d’autres endpoints selon besoin
}
