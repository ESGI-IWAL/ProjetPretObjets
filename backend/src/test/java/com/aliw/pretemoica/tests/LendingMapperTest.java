package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.aliw.pretemoica.dto.LendingDto;
import com.aliw.pretemoica.entity.LendingEntity;
import com.aliw.pretemoica.entity.LendingStatus;
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
    entity.setStatus(LendingStatus.COMPLETED);

    LendingDto dto = LendingMapper.toDto(entity);

    assertNotNull(dto);
    assertEquals(5L, dto.getId());
    assertEquals(2L, dto.getBorrowedById());
    assertEquals(3L, dto.getOfferedById());
    assertEquals(6L, dto.getObjectId());
    assertEquals(LendingStatus.COMPLETED, dto.getStatus());
    assertEquals(start, dto.getStartedAt());
    assertEquals(end, dto.getEndedAt());

    // Now toEntity
    LendingDto dto2 = new LendingDto();
    dto2.setId(7L);
    dto2.setBorrowedById(11L);
    dto2.setOfferedById(12L);
    dto2.setObjectId(13L);
    dto2.setStatus(LendingStatus.IN_PROGRESS);
    LocalDateTime s2 = LocalDateTime.of(2021, 2, 2, 9, 0);
    dto2.setStartedAt(s2);
    dto2.setEndedAt(s2.plusDays(1));

    LendingEntity e2 = LendingMapper.toEntity(dto2);
    assertNotNull(e2);
    assertEquals(7L, e2.getId());
    assertEquals(11L, e2.getBorrowedBy().getId());
    assertEquals(12L, e2.getOfferedBy().getId());
    assertEquals(13L, e2.getObject().getId());
    assertEquals(LendingStatus.IN_PROGRESS, e2.getStatus());
    assertEquals(s2, e2.getStartedAt());
    assertEquals(s2.plusDays(1), e2.getEndedAt());
  }

  @Test
  public void toEntityShouldKeepDefaultStartedAtWhenDtoHasNull() {
    LendingDto dto = new LendingDto();
    dto.setStartedAt(null);
    LendingEntity e = LendingMapper.toEntity(dto);
    assertNotNull(e.getStartedAt());
  }

  @Test
  public void listMappingHandlesNull() {
    assertNotNull(LendingMapper.toDtoList(null));
    assertEquals(0, LendingMapper.toDtoList(null).size());
    assertNotNull(LendingMapper.toEntityList(null));
    assertEquals(0, LendingMapper.toEntityList(null).size());
  }
}
