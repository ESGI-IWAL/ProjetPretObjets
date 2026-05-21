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
    obj.setOwnedBy(owner);
    obj.setStatus(ObjectEntity.ObjectStatus.LENT);
    obj.setStateOfWear(ObjectEntity.EObjectStateOfWear.GOOD);

    assertEquals("Test Object", obj.getName());
    assertEquals(owner, obj.getOwnedBy());
    assertEquals(ObjectEntity.ObjectStatus.LENT, obj.getStatus());
    assertEquals(ObjectEntity.EObjectStateOfWear.GOOD, obj.getStateOfWear());
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
  public void testEObjectStateOfWearEnum() {
    assertNotNull(ObjectEntity.EObjectStateOfWear.NEW);
    assertNotNull(ObjectEntity.EObjectStateOfWear.GOOD);
    assertNotNull(ObjectEntity.EObjectStateOfWear.WORN);
    assertNotNull(ObjectEntity.EObjectStateOfWear.DAMAGED);
  }
}
