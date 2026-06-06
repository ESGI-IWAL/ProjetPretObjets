package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.UserEntity;
import com.aliw.pretemoica.repository.ObjectRepository;
import com.aliw.pretemoica.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = AFTER_CLASS)
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class FixtureIntegrationTest {

  @Autowired private UserRepository userRepository;

  @Autowired private ObjectRepository objectRepository;

  @Test
  void shouldLoadFunctionalFixtures() {
    assertEquals(1L, userRepository.count());
    assertEquals(1L, objectRepository.count());

    UserEntity user = userRepository.findAll().get(0);
    ObjectEntity object = objectRepository.findAll().get(0);

    assertEquals("alice", user.getUsername());
    assertEquals("alice@example.com", user.getEmail());
    assertEquals("Chaise", object.getName());
    assertEquals("Chaise en bois", object.getDescription());
    assertNotNull(object.getWeight());
    assertNotNull(object.getDimensions());
    assertNotNull(object.getOwnedBy());
    assertEquals(user.getId(), object.getOwnedBy().getId());
    assertEquals(ObjectEntity.ObjectStateOfWear.GOOD, object.getStateOfWear());
    assertEquals(ObjectEntity.ObjectCategories.FURNITURE, object.getCategory());
    assertEquals(ObjectEntity.ObjectMaterial.WOOD, object.getMaterial());
  }
}
