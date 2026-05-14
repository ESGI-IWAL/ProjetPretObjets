package com.aliw.pretemoica.mapper;

import com.aliw.pretemoica.dto.UserDto;
import com.aliw.pretemoica.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class UserMapper {

  private UserMapper() {}

  public static UserDto toDto(UserEntity entity) {
    if (entity == null) {
      return null;
    }

    UserDto dto = new UserDto();
    dto.setId(entity.getId());
    dto.setUsername(entity.getUsername());
    dto.setEmail(entity.getEmail());
    dto.setRating(safeRating(entity));
    return dto;
  }

  public static UserEntity toEntity(UserDto dto) {
    if (dto == null) {
      return null;
    }

    UserEntity entity = new UserEntity();
    entity.setId(dto.getId());
    entity.setUsername(dto.getUsername());
    entity.setEmail(dto.getEmail());
    entity.setRating(dto.getRating() != null ? dto.getRating() : 0);
    return entity;
  }

  public static List<UserDto> toDtoList(List<UserEntity> entities) {
    if (entities == null) {
      return new ArrayList<>();
    }
    return entities.stream()
        .map(UserMapper::toDto)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public static List<UserEntity> toEntityList(List<UserDto> dtos) {
    if (dtos == null) {
      return new ArrayList<>();
    }
    return dtos.stream().map(UserMapper::toEntity).collect(Collectors.toCollection(ArrayList::new));
  }

  private static Integer safeRating(UserEntity entity) {
    try {
      return entity.getRating();
    } catch (IllegalStateException ex) {
      return null;
    }
  }
}
