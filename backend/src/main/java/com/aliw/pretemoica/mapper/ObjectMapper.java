package com.aliw.pretemoica.mapper;

import com.aliw.pretemoica.dto.ObjectDto;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ObjectMapper {

  private ObjectMapper() {}

  public static ObjectDto toDto(ObjectEntity entity) {
    if (entity == null) {
      return null;
    }

    ObjectDto dto = new ObjectDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setDescription(entity.getDescription());
    dto.setWeight(entity.getWeight());
    dto.setDimensions(entity.getDimensions());
    dto.setOwnedById(entity.getOwnedBy() != null ? entity.getOwnedBy().getId() : null);
    dto.setStatus(entity.getStatus());
    dto.setStateOfWear(entity.getStateOfWear());
    dto.setCategory(entity.getCategory());
    dto.setMaterial(entity.getMaterial());
    return dto;
  }

  public static ObjectEntity toEntity(ObjectDto dto) {
    if (dto == null) {
      return null;
    }

    ObjectEntity entity = new ObjectEntity();
    entity.setId(dto.getId());
    entity.setName(dto.getName());
    entity.setDescription(dto.getDescription());
    entity.setWeight(dto.getWeight());
    entity.setDimensions(dto.getDimensions());
    entity.setStatus(
        dto.getStatus() != null ? dto.getStatus() : ObjectEntity.ObjectStatus.AVAILABLE);
    entity.setStateOfWear(dto.getStateOfWear());
    entity.setCategory(dto.getCategory());
    entity.setMaterial(dto.getMaterial());
    if (dto.getOwnedById() != null) {
      entity.setOwnedBy(toUserReference(dto.getOwnedById()));
    }
    return entity;
  }

  public static List<ObjectDto> toDtoList(List<ObjectEntity> entities) {
    if (entities == null) {
      return new ArrayList<>();
    }
    return entities.stream()
        .map(ObjectMapper::toDto)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public static List<ObjectEntity> toEntityList(List<ObjectDto> dtos) {
    if (dtos == null) {
      return new ArrayList<>();
    }
    return dtos.stream()
        .map(ObjectMapper::toEntity)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private static UserEntity toUserReference(Long userId) {
    UserEntity user = new UserEntity();
    user.setId(userId);
    return user;
  }
}
