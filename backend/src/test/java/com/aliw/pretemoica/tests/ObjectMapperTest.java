package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.aliw.pretemoica.dto.ObjectDto;
import com.aliw.pretemoica.dto.UpdateObjectDto;
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
    entity.setDescription("Sports ball");
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
    assertEquals("Sports ball", dto.getDescription());
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
    dto.setDescription("Object description");
    dto.setWeight(3.4d);
    dto.setDimensions("20x10x5 cm");
    dto.setOwnedById(9L);
    dto.setStatus(null);
    dto.setStateOfWear(ObjectEntity.ObjectStateOfWear.NEW);

    ObjectEntity entity = ObjectMapper.toEntity(dto);

    assertNotNull(entity);
    assertEquals(4L, entity.getId());
    assertEquals("Obj", entity.getName());
    assertEquals("Object description", entity.getDescription());
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

  @Test
  public void toEntityFromUpdateShouldUpdateOnlyProvidedFields() {
    ObjectEntity existingEntity = new ObjectEntity();
    existingEntity.setId(5L);
    existingEntity.setName("Original Name");
    existingEntity.setDescription("Original Description");
    existingEntity.setWeight(2.0d);
    existingEntity.setDimensions("10x10x10 cm");
    existingEntity.setStateOfWear(ObjectEntity.ObjectStateOfWear.NEW);
    existingEntity.setCategory(ObjectEntity.ObjectCategories.ELECTRONICS);
    existingEntity.setMaterial(ObjectEntity.ObjectMaterial.PLASTIC);

    UpdateObjectDto updateDto = new UpdateObjectDto();
    updateDto.setName("Updated Name");
    updateDto.setWeight(3.5d);
    // description, dimensions, state, category, material are null (not updated)

    ObjectEntity updatedEntity = ObjectMapper.toEntityFromUpdate(updateDto, existingEntity);

    assertNotNull(updatedEntity);
    assertEquals(5L, updatedEntity.getId());
    assertEquals("Updated Name", updatedEntity.getName());
    assertEquals("Original Description", updatedEntity.getDescription());
    assertEquals(3.5d, updatedEntity.getWeight());
    assertEquals("10x10x10 cm", updatedEntity.getDimensions());
    assertEquals(ObjectEntity.ObjectStateOfWear.NEW, updatedEntity.getStateOfWear());
    assertEquals(ObjectEntity.ObjectCategories.ELECTRONICS, updatedEntity.getCategory());
    assertEquals(ObjectEntity.ObjectMaterial.PLASTIC, updatedEntity.getMaterial());
  }

  @Test
  public void toEntityFromUpdateWithNullDtoShouldReturnUnchangedEntity() {
    ObjectEntity entity = new ObjectEntity();
    entity.setId(6L);
    entity.setName("Name");

    ObjectEntity result = ObjectMapper.toEntityFromUpdate(null, entity);

    assertEquals(entity, result);
    assertEquals(6L, result.getId());
    assertEquals("Name", result.getName());
  }
}
