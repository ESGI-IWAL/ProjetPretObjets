package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.aliw.pretemoica.entity.LendingEntity;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.UserEntity;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

// Classe de test pour LendingEntity
class LendingEntityTest {

  @Test
  public void testDefaultConstructor() {
    LendingEntity lending = new LendingEntity();
    assertNotNull(lending);
    assertNull(lending.getId());
    assertNull(lending.getBorrowedBy());
    assertNull(lending.getOfferedBy());
    assertNull(lending.getObject());
    assertNotNull(lending.getStartedAt()); // should be set to now
    assertNull(lending.getEndedAt());
  }

  @Test
  public void testSettersAndGetters() {
    LendingEntity lending = new LendingEntity();
    UserEntity borrower = new UserEntity();
    borrower.setEmail("borrower@example.com");

    UserEntity offerer = new UserEntity();
    offerer.setEmail("offerer@example.com");

    ObjectEntity obj = new ObjectEntity();
    obj.setName("Test Object");

    LocalDateTime start = LocalDateTime.now();
    LocalDateTime end = LocalDateTime.now().plusDays(1);

    lending.setBorrowedBy(borrower);
    lending.setOfferedBy(offerer);
    lending.setObject(obj);
    lending.setStartedAt(start);
    lending.setEndedAt(end);

    assertEquals(borrower, lending.getBorrowedBy());
    assertEquals(offerer, lending.getOfferedBy());
    assertEquals(obj, lending.getObject());
    assertEquals(start, lending.getStartedAt());
    assertEquals(end, lending.getEndedAt());
  }
}
