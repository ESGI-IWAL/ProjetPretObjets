package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.aliw.pretemoica.entity.LendingHistoryEntity;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.repository.LendingHistoryRepository;
import com.aliw.pretemoica.service.LendingHistoryService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LendingHistoryServiceTest {

  @Mock private LendingHistoryRepository lendingHistoryRepository;

  @InjectMocks private LendingHistoryService lendingHistoryService;

  @Test
  public void createShouldDelegateToRepository() {
    LendingHistoryEntity e = new LendingHistoryEntity();
    when(lendingHistoryRepository.save(e)).thenReturn(e);

    LendingHistoryEntity res = lendingHistoryService.create(e);

    assertSame(e, res);
    verify(lendingHistoryRepository, times(1)).save(e);
  }

  @Test
  public void getAllShouldReturnList() {
    LendingHistoryEntity e1 = new LendingHistoryEntity();
    LendingHistoryEntity e2 = new LendingHistoryEntity();
    when(lendingHistoryRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

    List<LendingHistoryEntity> list = lendingHistoryService.getAll();

    assertEquals(2, list.size());
    verify(lendingHistoryRepository, times(1)).findAll();
  }

  @Test
  public void getByIdShouldReturnWhenFound() {
    LendingHistoryEntity e = new LendingHistoryEntity();
    e.setId(1L);
    when(lendingHistoryRepository.findById(1L)).thenReturn(Optional.of(e));

    LendingHistoryEntity found = lendingHistoryService.getById(1L);

    assertEquals(e, found);
  }

  @Test
  public void getByIdShouldThrowWhenNotFound() {
    when(lendingHistoryRepository.findById(11L)).thenReturn(Optional.empty());

    ResourceNotFoundException ex =
        assertThrows(ResourceNotFoundException.class, () -> lendingHistoryService.getById(11L));
    assertTrue(ex.getMessage().contains("11"));
  }

  @Test
  public void deleteShouldCallRepositoryDelete() {
    LendingHistoryEntity e = new LendingHistoryEntity();
    e.setId(8L);
    when(lendingHistoryRepository.findById(8L)).thenReturn(Optional.of(e));

    lendingHistoryService.delete(8L);

    verify(lendingHistoryRepository, times(1)).delete(e);
  }
}
