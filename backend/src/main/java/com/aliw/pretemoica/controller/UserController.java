package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.dto.UserDto;
import com.aliw.pretemoica.mapper.UserMapper;
import com.aliw.pretemoica.service.UserService;
import com.aliw.pretemoica.security.SecurityUtils;
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

  @GetMapping("/me/others")
  public java.util.List<UserDto> getAllUsersExceptCurrentUser() {
    Long currentUserId = SecurityUtils.getCurrentUserId();
    return UserMapper.toDtoList(userService.getAllExceptCurrentUser(currentUserId));
  }

  @GetMapping("/{id}")
  public UserDto getUserById(@PathVariable Long id) {
    return UserMapper.toDto(userService.getById(id));
  }

  @PostMapping
  public UserDto createUser(@RequestBody UserDto user) {
    return UserMapper.toDto(userService.create(UserMapper.toEntity(user)));
  }

  @PutMapping("/{id}")
  public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto user) {
    var existingUser = userService.getById(id);

    existingUser.setUsername(user.getUsername());
    existingUser.setEmail(user.getEmail());
    existingUser.setAvatar(user.getAvatar());
    existingUser.setDescription(user.getDescription());
    existingUser.setRating(user.getRating());

    return UserMapper.toDto(userService.create(existingUser));
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    userService.delete(id);
  }

  // Ajoute d’autres endpoints selon besoin

}
