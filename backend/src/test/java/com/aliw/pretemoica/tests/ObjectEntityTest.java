package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.UserEntity;
import org.junit.jupiter.api.Test;

public class ObjectEntityTest {

  @Test
  public void testDefaultConstructor() {
    ObjectEntity obj = new ObjectEntity();
    assertNotNull(obj);
    assertNull(obj.getId());
    assertNull(obj.getName());
    assertNull(obj.getDescription());
    assertNull(obj.getWeight());
    assertNull(obj.getDimensions());
    assertNull(obj.getOwnedBy());
    assertEquals(ObjectEntity.ObjectStatus.AVAILABLE, obj.getStatus());
    assertNull(obj.getStateOfWear());
  }

  @Test
  public void testSettersAndGetters() {
    ObjectEntity obj = new ObjectEntity();
    UserEntity owner = new UserEntity();
    owner.setEmail("owner@example.com");

    obj.setName("Test Object");
    obj.setDescription("Test description");
    obj.setWeight(2.5d);
    obj.setDimensions("10x20x30 cm");
    obj.setOwnedBy(owner);
    obj.setStatus(ObjectEntity.ObjectStatus.LENT);
    obj.setStateOfWear(ObjectEntity.ObjectStateOfWear.GOOD);

    assertEquals("Test Object", obj.getName());
    assertEquals("Test description", obj.getDescription());
    assertEquals(2.5d, obj.getWeight());
    assertEquals("10x20x30 cm", obj.getDimensions());
    assertEquals(owner, obj.getOwnedBy());
    assertEquals(ObjectEntity.ObjectStatus.LENT, obj.getStatus());
    assertEquals(ObjectEntity.ObjectStateOfWear.GOOD, obj.getStateOfWear());
  }

  @Test
  public void testObjectStatusEnum() {
    // Just to ensure enums are accessible
    assertNotNull(ObjectEntity.ObjectStatus.AVAILABLE);
    assertNotNull(ObjectEntity.ObjectStatus.LENT);
    assertNotNull(ObjectEntity.ObjectStatus.RESERVED);
    assertNotNull(ObjectEntity.ObjectStatus.UNAVAILABLE);
  }

  @Test
  public void testObjectStateOfWearEnum() {
    assertNotNull(ObjectEntity.ObjectStateOfWear.NEW);
    assertNotNull(ObjectEntity.ObjectStateOfWear.GOOD);
    assertNotNull(ObjectEntity.ObjectStateOfWear.WORN);
    assertNotNull(ObjectEntity.ObjectStateOfWear.DAMAGED);
  }
}
