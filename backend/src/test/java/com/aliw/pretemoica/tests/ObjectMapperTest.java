package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.aliw.pretemoica.dto.ObjectDto;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.UserEntity;
import com.aliw.pretemoica.mapper.ObjectMapper;
import org.junit.jupiter.api.Test;

public class ObjectMapperTest {

  @Test
  public void toDtoShouldMapFields() {
    ObjectEntity entity = new ObjectEntity();
    entity.setId(3L);
    entity.setName("Ball");
    UserEntity owner = new UserEntity();
    owner.setId(7L);
    entity.setOwnedBy(owner);
    entity.setStatus(ObjectEntity.ObjectStatus.LENT);
    entity.setStateOfWear(ObjectEntity.EObjectStateOfWear.GOOD);

    ObjectDto dto = ObjectMapper.toDto(entity);

    assertNotNull(dto);
    assertEquals(3L, dto.getId());
    assertEquals("Ball", dto.getName());
    assertEquals(7L, dto.getOwnedById());
    assertEquals(ObjectEntity.ObjectStatus.LENT, dto.getStatus());
    assertEquals(ObjectEntity.EObjectStateOfWear.GOOD, dto.getStateOfWear());
  }

  @Test
  public void toEntityShouldMapAndDefaultStatus() {
    ObjectDto dto = new ObjectDto();
    dto.setId(4L);
    dto.setName("Obj");
    dto.setOwnedById(9L);
    dto.setStatus(null);
    dto.setStateOfWear(ObjectEntity.EObjectStateOfWear.NEW);

    ObjectEntity entity = ObjectMapper.toEntity(dto);

    assertNotNull(entity);
    assertEquals(4L, entity.getId());
    assertEquals("Obj", entity.getName());
    assertNotNull(entity.getOwnedBy());
    assertEquals(9L, entity.getOwnedBy().getId());
    // when dto.status null, mapper sets AVAILABLE
    assertEquals(ObjectEntity.ObjectStatus.AVAILABLE, entity.getStatus());
    assertEquals(ObjectEntity.EObjectStateOfWear.NEW, entity.getStateOfWear());
  }

  @Test
  public void toDtoListAndToEntityListHandleNull() {
    assertNotNull(ObjectMapper.toDtoList(null));
    assertEquals(0, ObjectMapper.toDtoList(null).size());
    assertNotNull(ObjectMapper.toEntityList(null));
    assertEquals(0, ObjectMapper.toEntityList(null).size());
  }
}
