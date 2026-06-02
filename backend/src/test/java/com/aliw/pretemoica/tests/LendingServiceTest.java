package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.aliw.pretemoica.dto.LendingSearchDto;
import com.aliw.pretemoica.dto.ObjectInfoDisponibilityDto;
import com.aliw.pretemoica.dto.SearchLendingWithIdsObjectsDto;
import com.aliw.pretemoica.entity.LendingEntity;
import com.aliw.pretemoica.entity.LendingStatus;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.repository.LendingRepository;
import com.aliw.pretemoica.service.LendingService;
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
class LendingServiceTest {

  @Mock private LendingRepository lendingRepository;
  @Mock private ObjectService objectService;
  @Mock private UserService userService;

  @Mock private com.aliw.pretemoica.repository.ObjectRepository objectRepository;

  @InjectMocks private LendingService lendingService;

  @Test
  void createShouldDelegateToRepository() {
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
  void searchShouldDelegateToRepositoryWithNormalizedCriteria() {
    LendingSearchDto searchDto = new LendingSearchDto();
    searchDto.setObjectName("  Vélo  ");
    searchDto.setBorrowerName("  bob  ");
    searchDto.setStatus(LendingStatus.COMPLETED);

    LendingEntity lending = new LendingEntity();
    lending.setId(9L);
    when(lendingRepository.search("Vélo", "bob", null, null, "COMPLETED"))
        .thenReturn(List.of(lending));

    List<LendingEntity> result = lendingService.search(searchDto);

    assertEquals(1, result.size());
    assertEquals(lending, result.get(0));
    verify(lendingRepository, times(1)).search("Vélo", "bob", null, null, "COMPLETED");
  }

  @Test
  void getByIdShouldReturnWhenFound() {
    LendingEntity e = new LendingEntity();
    e.setId(1L);
    when(lendingRepository.findById(1L)).thenReturn(Optional.of(e));

    LendingEntity found = lendingService.getById(1L);

    assertEquals(e, found);
  }

  @Test
  void getByIdShouldThrowWhenNotFound() {
    when(lendingRepository.findById(7L)).thenReturn(Optional.empty());

    ResourceNotFoundException ex =
        assertThrows(ResourceNotFoundException.class, () -> lendingService.getById(7L));
    assertTrue(ex.getMessage().contains("7"));
  }

  @Test
  void deleteShouldCallRepositoryDelete() {
    LendingEntity e = new LendingEntity();
    e.setId(4L);
    when(lendingRepository.findById(4L)).thenReturn(Optional.of(e));

    lendingService.delete(4L);

    verify(lendingRepository, times(1)).delete(e);
  }

  @Test
  void searchObjectsDisponibilityShouldReturnAllObjectsWhenNull() {
    // Crée deux objets
    ObjectEntity obj1 = new ObjectEntity();
    obj1.setId(1L);
    ObjectEntity obj2 = new ObjectEntity();
    obj2.setId(2L);

    when(objectRepository.findAll()).thenReturn(Arrays.asList(obj1, obj2));
    when(lendingRepository.findAllLendingsGroupedByObject()).thenReturn(Arrays.asList());

    List<ObjectInfoDisponibilityDto> result = lendingService.searchObjectsDisponibility(null);

    assertEquals(2, result.size());
    assertEquals(1L, result.get(0).getId());
    assertEquals(2L, result.get(1).getId());
    verify(objectRepository, times(1)).findAll();
    verify(lendingRepository, times(1)).findAllLendingsGroupedByObject();
  }

  @Test
  void searchObjectsDisponibilityShouldReturnAllObjectsWhenIdsEmpty() {
    // Crée un objet
    ObjectEntity obj1 = new ObjectEntity();
    obj1.setId(5L);

    SearchLendingWithIdsObjectsDto searchDto = new SearchLendingWithIdsObjectsDto();
    searchDto.setIdsObject(Arrays.asList());

    when(objectRepository.findAll()).thenReturn(Arrays.asList(obj1));
    when(lendingRepository.findAllLendingsGroupedByObject()).thenReturn(Arrays.asList());

    List<ObjectInfoDisponibilityDto> result = lendingService.searchObjectsDisponibility(searchDto);

    assertEquals(1, result.size());
    assertEquals(5L, result.get(0).getId());
    verify(objectRepository, times(1)).findAll();
    verify(lendingRepository, times(1)).findAllLendingsGroupedByObject();
  }

  @Test
  void searchObjectsDisponibilityShouldReturnEmptyListWhenNoObjects() {
    when(objectRepository.findAll()).thenReturn(Arrays.asList());

    List<ObjectInfoDisponibilityDto> result = lendingService.searchObjectsDisponibility(null);

    assertEquals(0, result.size());
    verify(objectRepository, times(1)).findAll();
    verify(lendingRepository, never()).findAllLendingsGroupedByObject();
  }

  @Test
  void searchObjectsDisponibilityShouldReturnObjectsWithoutLendings() {
    SearchLendingWithIdsObjectsDto searchDto = new SearchLendingWithIdsObjectsDto();
    searchDto.setIdsObject(Arrays.asList(1L, 2L));

    when(lendingRepository.findLendingsForObjects(Arrays.asList(1L, 2L)))
        .thenReturn(Arrays.asList());

    List<ObjectInfoDisponibilityDto> result = lendingService.searchObjectsDisponibility(searchDto);

    assertEquals(2, result.size());
    assertEquals(1L, result.get(0).getId());
    assertEquals(2L, result.get(1).getId());
    assertNull(result.get(0).getNextLending());
    assertNull(result.get(0).getEndCurrentLending());
    assertNull(result.get(1).getNextLending());
    assertNull(result.get(1).getEndCurrentLending());
  }

  @Test
  void searchObjectsDisponibilityShouldReturnObjectWithCurrentLendingOnly() {
    SearchLendingWithIdsObjectsDto searchDto = new SearchLendingWithIdsObjectsDto();
    searchDto.setIdsObject(Arrays.asList(1L));

    LendingEntity currentLending = new LendingEntity();
    currentLending.setId(10L);
    currentLending.setStatus(LendingStatus.IN_PROGRESS);
    currentLending.setStartedAt(LocalDateTime.of(2026, 5, 20, 10, 0));
    currentLending.setEndedAt(LocalDateTime.of(2026, 5, 30, 18, 0));
    ObjectEntity obj = new ObjectEntity();
    obj.setId(1L);
    currentLending.setObject(obj);

    when(lendingRepository.findLendingsForObjects(Arrays.asList(1L)))
        .thenReturn(Arrays.asList(currentLending));

    List<ObjectInfoDisponibilityDto> result = lendingService.searchObjectsDisponibility(searchDto);

    assertEquals(1, result.size());
    assertEquals(1L, result.get(0).getId());
    assertEquals(LocalDateTime.of(2026, 5, 30, 18, 0), result.get(0).getEndCurrentLending());
    assertNull(result.get(0).getNextLending());
  }

  @Test
  void searchObjectsDisponibilityShouldReturnCurrentAndNextLending() {
    SearchLendingWithIdsObjectsDto searchDto = new SearchLendingWithIdsObjectsDto();
    searchDto.setIdsObject(Arrays.asList(1L));

    LendingEntity currentLending = new LendingEntity();
    currentLending.setId(10L);
    currentLending.setStatus(LendingStatus.IN_PROGRESS);
    currentLending.setStartedAt(LocalDateTime.of(2026, 5, 20, 10, 0));
    currentLending.setEndedAt(LocalDateTime.of(2026, 5, 30, 18, 0));
    ObjectEntity obj1 = new ObjectEntity();
    obj1.setId(1L);
    currentLending.setObject(obj1);

    LendingEntity nextLending = new LendingEntity();
    nextLending.setId(11L);
    nextLending.setStatus(LendingStatus.PENDING);
    nextLending.setStartedAt(LocalDateTime.of(2026, 5, 10, 9, 0));
    nextLending.setEndedAt(null);
    ObjectEntity obj2 = new ObjectEntity();
    obj2.setId(1L);
    nextLending.setObject(obj2);

    when(lendingRepository.findLendingsForObjects(Arrays.asList(1L)))
        .thenReturn(Arrays.asList(currentLending, nextLending));

    List<ObjectInfoDisponibilityDto> result = lendingService.searchObjectsDisponibility(searchDto);

    assertEquals(1, result.size());
    assertEquals(1L, result.get(0).getId());
    assertEquals(LocalDateTime.of(2026, 5, 30, 18, 0), result.get(0).getEndCurrentLending());
    assertEquals(LocalDateTime.of(2026, 5, 10, 9, 0), result.get(0).getNextLending());
  }

  @Test
  void searchObjectsDisponibilityShouldHandleMultipleObjects() {
    SearchLendingWithIdsObjectsDto searchDto = new SearchLendingWithIdsObjectsDto();
    searchDto.setIdsObject(Arrays.asList(1L, 2L));

    LendingEntity lending1 = new LendingEntity();
    lending1.setId(10L);
    lending1.setStatus(LendingStatus.IN_PROGRESS);
    lending1.setStartedAt(LocalDateTime.of(2026, 5, 20, 10, 0));
    lending1.setEndedAt(LocalDateTime.of(2026, 5, 30, 18, 0));
    ObjectEntity obj1 = new ObjectEntity();
    obj1.setId(1L);
    lending1.setObject(obj1);

    LendingEntity lending2 = new LendingEntity();
    lending2.setId(11L);
    lending2.setStatus(LendingStatus.COMPLETED);
    lending2.setStartedAt(LocalDateTime.of(2026, 5, 15, 14, 0));
    lending2.setEndedAt(LocalDateTime.of(2026, 5, 25, 16, 0));
    ObjectEntity obj2 = new ObjectEntity();
    obj2.setId(2L);
    lending2.setObject(obj2);

    when(lendingRepository.findLendingsForObjects(Arrays.asList(1L, 2L)))
        .thenReturn(Arrays.asList(lending1, lending2));

    List<ObjectInfoDisponibilityDto> result = lendingService.searchObjectsDisponibility(searchDto);

    assertEquals(2, result.size());
    assertEquals(1L, result.get(0).getId());
    assertEquals(LocalDateTime.of(2026, 5, 30, 18, 0), result.get(0).getEndCurrentLending());
    assertNull(result.get(0).getNextLending());

    assertEquals(2L, result.get(1).getId());
    assertEquals(LocalDateTime.of(2026, 5, 25, 16, 0), result.get(1).getEndCurrentLending());
    assertNull(result.get(1).getNextLending());
  }
}
