package com.aliw.pretemoica.mapper;

import com.aliw.pretemoica.dto.CreateLendingDto;
import com.aliw.pretemoica.dto.LendingDto;
import com.aliw.pretemoica.dto.UpdateLendingDto;
import com.aliw.pretemoica.entity.LendingEntity;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.UserEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
    dto.setBorrowedBy(UserMapper.toDto(entity.getBorrowedBy()));
    dto.setOfferedBy(UserMapper.toDto(entity.getOfferedBy()));
    dto.setObject(ObjectMapper.toDto(entity.getObject()));
    dto.setStartAt(entity.getStartedAt());
    dto.setEndAt(entity.getEndedAt());
    dto.setStatus(entity.getStatus() != null ? entity.getStatus().getValue() : null);
    return dto;
  }

  public static LendingEntity toEntity(LendingDto dto) {
    if (dto == null) {
      return null;
    }

    LendingEntity entity = new LendingEntity();
    entity.setId(dto.getId());
    entity.setStartedAt(dto.getStartAt() != null ? dto.getStartAt() : entity.getStartedAt());
    entity.setEndedAt(dto.getEndAt());

    entity.setBorrowedBy(UserMapper.toEntity(dto.getBorrowedBy()));
    entity.setOfferedBy(UserMapper.toEntity(dto.getOfferedBy()));
    entity.setObject(ObjectMapper.toEntity(dto.getObject()));
    return entity;
  }

  public static LendingEntity toEntity(CreateLendingDto dto) {
    if (dto == null) {
      return null;
    }

    LendingEntity entity = new LendingEntity();
    entity.setBorrowedBy(
        dto.getBorrowerId() != null ? toUserReference(parseLong(dto.getBorrowerId())) : null);
    entity.setObject(
        dto.getObjectId() != null ? toObjectReference(parseLong(dto.getObjectId())) : null);
    entity.setStartedAt(parseDateTime(dto.getStartDate()));
    entity.setEndedAt(parseDateTime(dto.getEndDate()));
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

  private static Long parseLong(String value) {
    return Long.parseLong(value);
  }

  public static LocalDateTime parseDateTime(String value) {
    if (value == null || value.isBlank()) {
      return null;
    }

    try {
      return LocalDateTime.parse(value);
    } catch (Exception ignored) {
    }

    try {
      return OffsetDateTime.parse(value).toLocalDateTime();
    } catch (Exception ignored) {
    }

    return LocalDate.parse(value).atStartOfDay();
  }

  public static LendingEntity toEntity(UpdateLendingDto dto) {
    if (dto == null) {
      return null;
    }

    LendingEntity entity = new LendingEntity();

    // On n'autorise pas la modification de l'objet emprunté ni de l'emprunteur via l'endpoint
    // d'update. Seules les dates sont prises en compte ici.
    if (dto.getStartDate() != null) {
      entity.setStartedAt(parseDateTime(dto.getStartDate()));
    }

    if (dto.getEndDate() != null) {
      entity.setEndedAt(parseDateTime(dto.getEndDate()));
    }

    return entity;
  }
}
