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
    entity.setWeight(1.2d);
    entity.setDimensions("15x15x15 cm");
    UserEntity owner = new UserEntity();
    owner.setId(7L);
    entity.setOwnedBy(owner);
    entity.setStatus(ObjectEntity.ObjectStatus.LENT);
    entity.setStateOfWear(ObjectEntity.ObjectStateOfWear.GOOD);

    ObjectDto dto = ObjectMapper.toDto(entity);

    assertNotNull(dto);
    assertEquals(3L, dto.getId());
    assertEquals("Ball", dto.getName());
    assertEquals(1.2d, dto.getWeight());
    assertEquals("15x15x15 cm", dto.getDimensions());
    assertEquals(7L, dto.getOwnedById());
    assertEquals(ObjectEntity.ObjectStatus.LENT, dto.getStatus());
    assertEquals(ObjectEntity.ObjectStateOfWear.GOOD, dto.getStateOfWear());
  }

  @Test
  public void toEntityShouldMapAndDefaultStatus() {
    ObjectDto dto = new ObjectDto();
    dto.setId(4L);
    dto.setName("Obj");
    dto.setWeight(3.4d);
    dto.setDimensions("20x10x5 cm");
    dto.setOwnedById(9L);
    dto.setStatus(null);
    dto.setStateOfWear(ObjectEntity.ObjectStateOfWear.NEW);

    ObjectEntity entity = ObjectMapper.toEntity(dto);

    assertNotNull(entity);
    assertEquals(4L, entity.getId());
    assertEquals("Obj", entity.getName());
    assertEquals(3.4d, entity.getWeight());
    assertEquals("20x10x5 cm", entity.getDimensions());
    assertNotNull(entity.getOwnedBy());
    assertEquals(9L, entity.getOwnedBy().getId());
    // when dto.status null, mapper sets AVAILABLE
    assertEquals(ObjectEntity.ObjectStatus.AVAILABLE, entity.getStatus());
    assertEquals(ObjectEntity.ObjectStateOfWear.NEW, entity.getStateOfWear());
  }

  @Test
  public void toDtoListAndToEntityListHandleNull() {
    assertNotNull(ObjectMapper.toDtoList(null));
    assertEquals(0, ObjectMapper.toDtoList(null).size());
    assertNotNull(ObjectMapper.toEntityList(null));
    assertEquals(0, ObjectMapper.toEntityList(null).size());
  }
}
