package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.aliw.pretemoica.dto.CreateLendingDto;
import com.aliw.pretemoica.dto.LendingDto;
import com.aliw.pretemoica.entity.LendingEntity;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.UserEntity;
import com.aliw.pretemoica.mapper.LendingMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class LendingMapperTest {

  @Test
  public void toDtoAndToEntityShouldMapIdsAndDates() {
    LendingEntity entity = new LendingEntity();
    entity.setId(5L);
    UserEntity b = new UserEntity();
    b.setId(2L);
    entity.setBorrowedBy(b);
    UserEntity o = new UserEntity();
    o.setId(3L);
    entity.setOfferedBy(o);
    ObjectEntity obj = new ObjectEntity();
    obj.setId(6L);
    entity.setObject(obj);
    LocalDateTime start = LocalDateTime.of(2020, 1, 1, 10, 0);
    LocalDateTime end = LocalDateTime.of(2020, 1, 2, 10, 0);
    entity.setStartedAt(start);
    entity.setEndedAt(end);

    LendingDto dto = LendingMapper.toDto(entity);

    assertNotNull(dto);
    assertEquals(5L, dto.getId());
    assertEquals(2L, dto.getBorrowedById());
    assertEquals(3L, dto.getOfferedById());
    assertEquals(6L, dto.getObjectId());
    assertEquals(start, dto.getStartAt());
    assertEquals(end, dto.getEndAt());

    // Now toEntity
    LendingDto dto2 = new LendingDto();
    dto2.setId(7L);
    dto2.setBorrowedById(11L);
    dto2.setOfferedById(12L);
    dto2.setObjectId(13L);
    LocalDateTime s2 = LocalDateTime.of(2021, 2, 2, 9, 0);
    dto2.setStartAt(s2);
    dto2.setEndAt(s2.plusDays(1));

    LendingEntity e2 = LendingMapper.toEntity(dto2);
    assertNotNull(e2);
    assertEquals(7L, e2.getId());
    assertEquals(11L, e2.getBorrowedBy().getId());
    assertEquals(12L, e2.getOfferedBy().getId());
    assertEquals(13L, e2.getObject().getId());
    assertEquals(s2, e2.getStartedAt());
    assertEquals(s2.plusDays(1), e2.getEndedAt());
  }

  @Test
  public void toEntityShouldKeepDefaultStartedAtWhenDtoHasNull() {
    LendingDto dto = new LendingDto();
    dto.setStartAt(null);
    LendingEntity e = LendingMapper.toEntity(dto);
    assertNotNull(e.getStartedAt());
  }

  @Test
  public void createDtoShouldMapBorrowerObjectAndDates() {
    CreateLendingDto dto = new CreateLendingDto("13", "11", "2024-01-01", "2024-01-02T10:00:00Z");

    LendingEntity entity = LendingMapper.toEntity(dto);

    assertNotNull(entity);
    assertNotNull(entity.getBorrowedBy());
    assertEquals(11L, entity.getBorrowedBy().getId());
    assertNotNull(entity.getObject());
    assertEquals(13L, entity.getObject().getId());
    assertEquals(LocalDateTime.of(2024, 1, 1, 0, 0), entity.getStartedAt());
    assertEquals(LocalDateTime.of(2024, 1, 2, 10, 0), entity.getEndedAt());
  }

  @Test
  public void listMappingHandlesNull() {
    assertNotNull(LendingMapper.toDtoList(null));
    assertEquals(0, LendingMapper.toDtoList(null).size());
    assertNotNull(LendingMapper.toEntityList(null));
    assertEquals(0, LendingMapper.toEntityList(null).size());
  }
}
