package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.aliw.pretemoica.dto.UserDto;
import com.aliw.pretemoica.entity.UserEntity;
import com.aliw.pretemoica.mapper.UserMapper;
import org.junit.jupiter.api.Test;

public class UserMapperTest {

  @Test
  public void toDtoShouldMapFieldsAndSafeRating() {
    UserEntity entity = new UserEntity();
    entity.setId(1L);
    entity.setUsername("u");
    entity.setEmail("e@e.com");
    // simulate uninitialized rating -> cause getRating() to throw
    entity.setRating(null);

    UserDto dto = UserMapper.toDto(entity);

    assertNotNull(dto);
    assertEquals(1L, dto.getId());
    assertEquals("u", dto.getUsername());
    assertEquals("e@e.com", dto.getEmail());
    // safeRating should catch IllegalStateException and set null
    assertNull(dto.getRating());
  }

  @Test
  public void toEntityShouldMapFieldsAndDefaultRating() {
    UserDto dto = new UserDto();
    dto.setId(2L);
    dto.setUsername("name");
    dto.setEmail("x@x.com");
    dto.setRating(null);

    UserEntity entity = UserMapper.toEntity(dto);

    assertNotNull(entity);
    assertEquals(2L, entity.getId());
    assertEquals("name", entity.getUsername());
    assertEquals("x@x.com", entity.getEmail());
    // when DTO rating null, mapper sets default 0
    assertEquals(0, entity.getRating());
  }

  @Test
  public void toDtoListShouldReturnEmptyWhenNull() {
    assertNotNull(UserMapper.toDtoList(null));
    assertEquals(0, UserMapper.toDtoList(null).size());
  }

  @Test
  public void toEntityListShouldReturnEmptyWhenNull() {
    assertNotNull(UserMapper.toEntityList(null));
    assertEquals(0, UserMapper.toEntityList(null).size());
  }
}
