package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.aliw.pretemoica.dto.LendingHistoryDto;
import com.aliw.pretemoica.entity.LendingHistoryEntity;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.UserEntity;
import com.aliw.pretemoica.mapper.LendingHistoryMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class LendingHistoryMapperTest {

  @Test
  public void toDtoAndToEntityShouldMapFields() {
    LendingHistoryEntity entity = new LendingHistoryEntity();
    entity.setId(21L);
    UserEntity b = new UserEntity();
    b.setId(31L);
    entity.setBorrowedBy(b);
    UserEntity o = new UserEntity();
    o.setId(32L);
    entity.setOfferedBy(o);
    ObjectEntity obj = new ObjectEntity();
    obj.setId(41L);
    entity.setObject(obj);
    LocalDateTime start = LocalDateTime.of(2019, 3, 3, 8, 0);
    LocalDateTime end = LocalDateTime.of(2019, 3, 4, 8, 0);
    entity.setStartedAt(start);
    entity.setEndedAt(end);

    LendingHistoryDto dto = LendingHistoryMapper.toDto(entity);

    assertNotNull(dto);
    assertEquals(21L, dto.getId());
    assertEquals(31L, dto.getBorrowedById());
    assertEquals(32L, dto.getOfferedById());
    assertEquals(41L, dto.getObjectId());
    assertEquals(start, dto.getStartedAt());
    assertEquals(end, dto.getEndedAt());

    LendingHistoryDto dto2 = new LendingHistoryDto();
    dto2.setId(22L);
    dto2.setBorrowedById(51L);
    dto2.setOfferedById(52L);
    dto2.setObjectId(61L);
    LocalDateTime s2 = LocalDateTime.of(2022, 4, 4, 7, 0);
    dto2.setStartedAt(s2);
    dto2.setEndedAt(s2.plusDays(1));

    LendingHistoryEntity e2 = LendingHistoryMapper.toEntity(dto2);
    assertNotNull(e2);
    assertEquals(22L, e2.getId());
    assertEquals(51L, e2.getBorrowedBy().getId());
    assertEquals(52L, e2.getOfferedBy().getId());
    assertEquals(61L, e2.getObject().getId());
    assertEquals(s2, e2.getStartedAt());
    assertEquals(s2.plusDays(1), e2.getEndedAt());
  }

  @Test
  public void toEntityShouldKeepDefaultStartedAtWhenDtoHasNull() {
    LendingHistoryDto dto = new LendingHistoryDto();
    dto.setStartedAt(null);
    LendingHistoryEntity e = LendingHistoryMapper.toEntity(dto);
    assertNotNull(e.getStartedAt());
  }

  @Test
  public void listMappingHandlesNull() {
    assertNotNull(LendingHistoryMapper.toDtoList(null));
    assertEquals(0, LendingHistoryMapper.toDtoList(null).size());
    assertNotNull(LendingHistoryMapper.toEntityList(null));
    assertEquals(0, LendingHistoryMapper.toEntityList(null).size());
  }
}

