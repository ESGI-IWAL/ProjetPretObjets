package com.aliw.pretemoica.mapper;

import com.aliw.pretemoica.dto.LendingDto;
import com.aliw.pretemoica.entity.LendingEntity;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class LendingMapper {

  private LendingMapper() {}

  public static LendingDto toDto(LendingEntity entity) {
    if (entity == null) {
      return null;
    }

    LendingDto dto = new LendingDto();
    dto.setId(entity.getId());
    dto.setBorrowedById(entity.getBorrowedBy() != null ? entity.getBorrowedBy().getId() : null);
    dto.setOfferedById(entity.getOfferedBy() != null ? entity.getOfferedBy().getId() : null);
    dto.setObjectId(entity.getObject() != null ? entity.getObject().getId() : null);
    dto.setStartedAt(entity.getStartedAt());
    dto.setEndedAt(entity.getEndedAt());
    return dto;
  }

  public static LendingEntity toEntity(LendingDto dto) {
    if (dto == null) {
      return null;
    }

    LendingEntity entity = new LendingEntity();
    entity.setId(dto.getId());
    entity.setStartedAt(dto.getStartedAt() != null ? dto.getStartedAt() : entity.getStartedAt());
    entity.setEndedAt(dto.getEndedAt());

    if (dto.getBorrowedById() != null) {
      entity.setBorrowedBy(toUserReference(dto.getBorrowedById()));
    }
    if (dto.getOfferedById() != null) {
      entity.setOfferedBy(toUserReference(dto.getOfferedById()));
    }
    if (dto.getObjectId() != null) {
      entity.setObject(toObjectReference(dto.getObjectId()));
    }
    return entity;
  }

  public static List<LendingDto> toDtoList(List<LendingEntity> entities) {
    if (entities == null) {
      return new ArrayList<>();
    }
    return entities.stream()
        .map(LendingMapper::toDto)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  public static List<LendingEntity> toEntityList(List<LendingDto> dtos) {
    if (dtos == null) {
      return new ArrayList<>();
    }
    return dtos.stream()
        .map(LendingMapper::toEntity)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private static UserEntity toUserReference(Long userId) {
    UserEntity user = new UserEntity();
    user.setId(userId);
    return user;
  }

  private static ObjectEntity toObjectReference(Long objectId) {
    ObjectEntity object = new ObjectEntity();
    object.setId(objectId);
    return object;
  }
}
