package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.aliw.pretemoica.dto.CreateLendingDto;
import com.aliw.pretemoica.dto.UpdateLendingDto;
import com.aliw.pretemoica.entity.LendingEntity;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.UserEntity;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.repository.LendingRepository;
import com.aliw.pretemoica.service.LendingService;
import com.aliw.pretemoica.service.ObjectService;
import com.aliw.pretemoica.service.UserService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LendingServiceTest {

  @Mock private LendingRepository lendingRepository;
  @Mock private ObjectService objectService;
  @Mock private UserService userService;

  @InjectMocks private LendingService lendingService;

  @Test
  public void createShouldDelegateToRepository() {
    LendingEntity e = new LendingEntity();
    when(lendingRepository.save(e)).thenReturn(e);

    LendingEntity res = lendingService.create(e);

    assertSame(e, res);
    verify(lendingRepository, times(1)).save(e);
  }

  @Test
  public void createFromDtoShouldResolveRelationsAndDates() {
    CreateLendingDto dto = new CreateLendingDto("6", "2", "2024-01-01", "2024-01-02T10:00:00Z");

    UserEntity borrower = new UserEntity();
    borrower.setId(2L);
    UserEntity owner = new UserEntity();
    owner.setId(3L);

    ObjectEntity object = new ObjectEntity();
    object.setId(6L);
    object.setOwnedBy(owner);

    when(userService.getById(2L)).thenReturn(borrower);
    when(objectService.getById(6L)).thenReturn(object);
    when(lendingRepository.save(any(LendingEntity.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    LendingEntity created = lendingService.create(dto);

    assertNotNull(created);
    assertSame(borrower, created.getBorrowedBy());
    assertSame(owner, created.getOfferedBy());
    assertSame(object, created.getObject());
    assertEquals(LocalDateTime.of(2024, 1, 1, 0, 0), created.getStartedAt());
    assertEquals(LocalDateTime.of(2024, 1, 2, 10, 0), created.getEndedAt());
    verify(userService, times(1)).getById(2L);
    verify(objectService, times(1)).getById(6L);
    verify(lendingRepository, times(1)).save(any(LendingEntity.class));
  }

  @Test
  public void updateShouldApplyPartialChangesAndKeepUnspecifiedValues() {
    LendingEntity existing = new LendingEntity();
    existing.setId(9L);
    existing.setStartedAt(LocalDateTime.of(2024, 1, 1, 8, 0));
    existing.setEndedAt(LocalDateTime.of(2024, 1, 5, 8, 0));

    UserEntity borrower = new UserEntity();
    borrower.setId(20L);
    existing.setBorrowedBy(borrower);

    UserEntity owner = new UserEntity();
    owner.setId(30L);
    existing.setOfferedBy(owner);

    ObjectEntity object = new ObjectEntity();
    object.setId(40L);
    object.setOwnedBy(owner);
    existing.setObject(object);

    when(lendingRepository.findById(9L)).thenReturn(Optional.of(existing));
    when(lendingRepository.save(any(LendingEntity.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    UpdateLendingDto updateDto =
        new UpdateLendingDto("2024-03-01T09:00:00", "2024-03-02T10:00:00Z", null);

    LendingEntity updated = lendingService.update(9L, updateDto);

    assertSame(existing, updated);
    // borrower/object/offeredBy must rester inchangés
    assertSame(borrower, updated.getBorrowedBy());
    assertSame(object, updated.getObject());
    assertSame(owner, updated.getOfferedBy());
    assertEquals(LocalDateTime.of(2024, 3, 1, 9, 0), updated.getStartedAt());
    assertEquals(LocalDateTime.of(2024, 3, 2, 10, 0), updated.getEndedAt());
    verify(userService, times(0)).getById(anyLong());
    verify(objectService, times(0)).getById(anyLong());
    verify(lendingRepository, times(1)).save(existing);
  }

  @Test
  public void updateShouldThrowWhenLendingDoesNotExist() {
    when(lendingRepository.findById(12L)).thenReturn(Optional.empty());

    assertThrows(
        ResourceNotFoundException.class,
        () -> lendingService.update(12L, new UpdateLendingDto(null, null, null)));
  }

  @Test
  public void getAllShouldReturnList() {
    LendingEntity e1 = new LendingEntity();
    LendingEntity e2 = new LendingEntity();
    when(lendingRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

    List<LendingEntity> list = lendingService.getAll();

    assertEquals(2, list.size());
    verify(lendingRepository, times(1)).findAll();
  }

  @Test
  public void getByIdShouldReturnWhenFound() {
    LendingEntity e = new LendingEntity();
    e.setId(1L);
    when(lendingRepository.findById(1L)).thenReturn(Optional.of(e));

    LendingEntity found = lendingService.getById(1L);

    assertEquals(e, found);
  }

  @Test
  public void getByIdShouldThrowWhenNotFound() {
    when(lendingRepository.findById(7L)).thenReturn(Optional.empty());

    ResourceNotFoundException ex =
        assertThrows(ResourceNotFoundException.class, () -> lendingService.getById(7L));
    assertTrue(ex.getMessage().contains("7"));
  }

  @Test
  public void deleteShouldCallRepositoryDelete() {
    LendingEntity e = new LendingEntity();
    e.setId(4L);
    when(lendingRepository.findById(4L)).thenReturn(Optional.of(e));

    lendingService.delete(4L);

    verify(lendingRepository, times(1)).delete(e);
  }
}
