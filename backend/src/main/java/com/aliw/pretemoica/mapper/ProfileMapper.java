package com.aliw.pretemoica.mapper;

import com.aliw.pretemoica.dto.UserDto;
import com.aliw.pretemoica.entity.UserEntity;

public class ProfileMapper {
  public static UserDto toUserDto(UserEntity profile) {
    if (profile == null) {
      return null;
    }

    UserDto userDto = new UserDto();
    userDto.setId(profile.getId());
    userDto.setUsername(profile.getUsername());
    userDto.setEmail(profile.getEmail());
    userDto.setRating(safeRating(profile));
    return userDto;
  }

  private static Integer safeRating(UserEntity profile) {
    try {
      return profile.getRating();
    } catch (IllegalStateException ex) {
      return null;
    }
  }
}
