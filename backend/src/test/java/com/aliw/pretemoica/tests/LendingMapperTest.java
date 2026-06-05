package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.aliw.pretemoica.dto.CreateLendingDto;
import com.aliw.pretemoica.dto.LendingDto;
import com.aliw.pretemoica.dto.ObjectDto;
import com.aliw.pretemoica.dto.UserDto;
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
    assertNotNull(dto.getBorrowedBy());
    assertNotNull(dto.getOfferedBy());
    assertNotNull(dto.getObject());
    assertEquals(2L, dto.getBorrowedBy().getId());
    assertEquals(3L, dto.getOfferedBy().getId());
    assertEquals(6L, dto.getObject().getId());
    assertEquals(start, dto.getStartedAt()); // FIX: getStartAt() -> getStartedAt()
    assertEquals(end, dto.getEndedAt()); // FIX: getEndAt() -> getEndedAt()

    // Now toEntity
    LendingDto dto2 = new LendingDto();
    dto2.setId(7L);
    // FIX: UserDto attend 6 paramètres (Long, String, String, Integer, String, String)
    dto2.setBorrowedBy(new UserDto(11L, "borrower", "borrower@example.com", 5, null, null));
    dto2.setOfferedBy(new UserDto(12L, "offerer", "offerer@example.com", 7, null, null));
    // FIX: ObjectEntity.ObjectStatus n'existe pas — à adapter selon ton ObjectDto
    ObjectDto objDto = new ObjectDto();
    objDto.setId(13L);
    objDto.setName("object");
    objDto.setOwnedById(99L);
    dto2.setObject(objDto);
    LocalDateTime s2 = LocalDateTime.of(2021, 2, 2, 9, 0);
    dto2.setStartedAt(s2); // FIX: setStartAt() -> setStartedAt()
    dto2.setEndedAt(s2.plusDays(1)); // FIX: setEndAt() -> setEndedAt()

    LendingEntity e2 = LendingMapper.toEntity(dto2);
    assertNotNull(e2);
    assertEquals(7L, e2.getId());
    assertEquals(11L, e2.getBorrowedBy().getId());
    assertEquals("borrower", e2.getBorrowedBy().getUsername());
    assertEquals(12L, e2.getOfferedBy().getId());
    assertEquals("offerer", e2.getOfferedBy().getUsername());
    assertEquals(13L, e2.getObject().getId());
    assertEquals("object", e2.getObject().getName());
    assertEquals(s2, e2.getStartedAt());
    assertEquals(s2.plusDays(1), e2.getEndedAt());
  }

  @Test
  public void toEntityShouldKeepDefaultStartedAtWhenDtoHasNull() {
    LendingDto dto = new LendingDto();
    dto.setStartedAt(null); // FIX: setStartAt() -> setStartedAt()
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
