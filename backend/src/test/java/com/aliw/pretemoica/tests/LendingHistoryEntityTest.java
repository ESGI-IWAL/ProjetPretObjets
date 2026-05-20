package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.aliw.pretemoica.entity.LendingHistoryEntity;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.UserEntity;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class LendingHistoryEntityTest {

  @Test
  public void testDefaultConstructor() {
    LendingHistoryEntity history = new LendingHistoryEntity();
    assertNotNull(history);
    assertNull(history.getId());
    assertNull(history.getBorrowedBy());
    assertNull(history.getOfferedBy());
    assertNull(history.getObject());
    assertNotNull(history.getStartedAt()); // should be set to now
    assertNull(history.getEndedAt());
  }

  @Test
  public void testSettersAndGetters() {
    LendingHistoryEntity history = new LendingHistoryEntity();
    UserEntity borrower = new UserEntity();
    borrower.setEmail("borrower@example.com");

    UserEntity offerer = new UserEntity();
    offerer.setEmail("offerer@example.com");

    ObjectEntity obj = new ObjectEntity();
    obj.setName("Test Object");

    LocalDateTime start = LocalDateTime.now();
    LocalDateTime end = LocalDateTime.now().plusDays(1);

    history.setBorrowedBy(borrower);
    history.setOfferedBy(offerer);
    history.setObject(obj);
    history.setStartedAt(start);
    history.setEndedAt(end);

    assertEquals(borrower, history.getBorrowedBy());
    assertEquals(offerer, history.getOfferedBy());
    assertEquals(obj, history.getObject());
    assertEquals(start, history.getStartedAt());
    assertEquals(end, history.getEndedAt());
  }

  @Test
  public void testHasDateErrorWhenStartAfterEnd() {
    LendingHistoryEntity history = new LendingHistoryEntity();
    LocalDateTime start = LocalDateTime.now().plusDays(1);
    LocalDateTime end = LocalDateTime.now();

    history.setStartedAt(start);
    history.setEndedAt(end);

    assertTrue(history.hasDateError());
  }

  @Test
  public void testHasDateErrorWhenStartBeforeEnd() {
    LendingHistoryEntity history = new LendingHistoryEntity();
    LocalDateTime start = LocalDateTime.now();
    LocalDateTime end = LocalDateTime.now().plusDays(1);

    history.setStartedAt(start);
    history.setEndedAt(end);

    assertFalse(history.hasDateError());
  }

  @Test
  public void testHasDateErrorWhenStartEqualsEnd() {
    LendingHistoryEntity history = new LendingHistoryEntity();
    LocalDateTime time = LocalDateTime.now();

    history.setStartedAt(time);
    history.setEndedAt(time);

    assertFalse(history.hasDateError()); // not after, so false
  }

  @Test
  public void testHasDateErrorWhenStartNull() {
    LendingHistoryEntity history = new LendingHistoryEntity();
    history.setStartedAt(null);
    history.setEndedAt(LocalDateTime.now());

    assertFalse(history.hasDateError());
  }

  @Test
  public void testHasDateErrorWhenEndNull() {
    LendingHistoryEntity history = new LendingHistoryEntity();
    history.setStartedAt(LocalDateTime.now());
    history.setEndedAt(null);

    assertFalse(history.hasDateError());
  }
}
