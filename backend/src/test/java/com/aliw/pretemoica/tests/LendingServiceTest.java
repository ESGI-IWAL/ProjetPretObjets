package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.aliw.pretemoica.dto.LendingSearchDto;
import com.aliw.pretemoica.entity.LendingEntity;
import com.aliw.pretemoica.entity.LendingStatus;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.repository.LendingRepository;
import com.aliw.pretemoica.service.LendingService;
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
  public void getAllShouldReturnList() {
    LendingEntity e1 = new LendingEntity();
    LendingEntity e2 = new LendingEntity();
    when(lendingRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

    List<LendingEntity> list = lendingService.getAll();

    assertEquals(2, list.size());
    verify(lendingRepository, times(1)).findAll();
  }

  @Test
  public void searchShouldDelegateToRepositoryWithNormalizedCriteria() {
    LendingSearchDto searchDto = new LendingSearchDto();
    searchDto.setObjectName("  Vélo  ");
    searchDto.setBorrowerName("  bob  ");
    searchDto.setStatus(LendingStatus.COMPLETED);

    LendingEntity lending = new LendingEntity();
    lending.setId(9L);
    when(lendingRepository.search("Vélo", "bob", null, null, LendingStatus.COMPLETED))
        .thenReturn(List.of(lending));

    List<LendingEntity> result = lendingService.search(searchDto);

    assertEquals(1, result.size());
    assertEquals(lending, result.get(0));
    verify(lendingRepository, times(1)).search("Vélo", "bob", null, null, LendingStatus.COMPLETED);
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
