package com.aliw.pretemoica.controller;

import com.aliw.pretemoica.dto.UserDto;
import com.aliw.pretemoica.mapper.ProfileMapper;
import com.aliw.pretemoica.service.UserService;
import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
public class ProfileController {

  private final UserService userService;

  public ProfileController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<UserDto> getMyProfile(Principal principal) {
    if (principal == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    try {
      return ResponseEntity.ok(
          ProfileMapper.toUserDto(userService.getMyProfile(principal.getName())));
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping
  public ResponseEntity<UserDto> updateMyProfile(
      Principal principal, @RequestBody UserDto userDto) {
    if (principal == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    try {
      return ResponseEntity.ok(
          ProfileMapper.toUserDto(userService.updateMyProfile(principal.getName(), userDto)));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
  }
}
