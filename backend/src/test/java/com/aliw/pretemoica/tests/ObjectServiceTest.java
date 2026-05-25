package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.aliw.pretemoica.dto.ObjectSearchDto;
import com.aliw.pretemoica.dto.UpdateObjectDto;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.ObjectEntity.ObjectCategories;
import com.aliw.pretemoica.entity.ObjectEntity.ObjectMaterial;
import com.aliw.pretemoica.entity.ObjectEntity.ObjectStateOfWear;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.repository.ObjectRepository;
import com.aliw.pretemoica.service.ObjectService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ObjectServiceTest {

  @Mock private ObjectRepository objectRepository;

  @InjectMocks private ObjectService objectService;

  @Test
  void createShouldDelegateToRepository() {
    ObjectEntity o = new ObjectEntity();
    o.setName("Obj");
    when(objectRepository.save(o)).thenReturn(o);

    ObjectEntity res = objectService.create(o);

    assertSame(o, res);
    verify(objectRepository, times(1)).save(o);
  }

  @Test
  void getAllShouldReturnList() {
    ObjectEntity o1 = new ObjectEntity();
    ObjectEntity o2 = new ObjectEntity();
    when(objectRepository.findAll()).thenReturn(Arrays.asList(o1, o2));

    List<ObjectEntity> list = objectService.getAll();

    assertEquals(2, list.size());
    verify(objectRepository, times(1)).findAll();
  }

  @Test
  void getByIdShouldReturnWhenFound() {
    ObjectEntity o = new ObjectEntity();
    o.setId(1L);
    when(objectRepository.findById(1L)).thenReturn(Optional.of(o));

    ObjectEntity found = objectService.getById(1L);

    assertEquals(o, found);
  }

  @Test
  void getByIdShouldThrowWhenNotFound() {
    when(objectRepository.findById(9L)).thenReturn(Optional.empty());

    ResourceNotFoundException ex =
        assertThrows(ResourceNotFoundException.class, () -> objectService.getById(9L));
    assertTrue(ex.getMessage().contains("9"));
  }

  @Test
  void deleteShouldCallRepositoryDelete() {
    ObjectEntity o = new ObjectEntity();
    o.setId(3L);
    when(objectRepository.findById(3L)).thenReturn(Optional.of(o));

    objectService.delete(3L);

    verify(objectRepository, times(1)).delete(o);
  }

  @Test
  void updateShouldUpdateObjectWhenFound() {
    ObjectEntity existingObject = new ObjectEntity();
    existingObject.setId(5L);
    existingObject.setName("Original Name");
    existingObject.setDescription("Original Description");
    existingObject.setWeight(2.0d);

    UpdateObjectDto updateDto = new UpdateObjectDto();
    updateDto.setName("Updated Name");
    updateDto.setWeight(3.5d);

    when(objectRepository.findById(5L)).thenReturn(Optional.of(existingObject));
    when(objectRepository.save(any(ObjectEntity.class))).thenReturn(existingObject);

    objectService.update(5L, updateDto);

    verify(objectRepository, times(1)).findById(5L);
    verify(objectRepository, times(1)).save(any(ObjectEntity.class));
  }

  @Test
  void updateShouldThrowWhenObjectNotFound() {
    UpdateObjectDto updateDto = new UpdateObjectDto();
    when(objectRepository.findById(99L)).thenReturn(Optional.empty());

    ResourceNotFoundException ex =
        assertThrows(ResourceNotFoundException.class, () -> objectService.update(99L, updateDto));
    assertTrue(ex.getMessage().contains("99"));
  }

  @Test
  void searchWithNullDtoShouldReturnAllObjects() {
    ObjectEntity o1 = new ObjectEntity();
    when(objectRepository.findAll()).thenReturn(Arrays.asList(o1));

    List<ObjectEntity> result = objectService.search(null);

    assertEquals(1, result.size());
    verify(objectRepository, times(1)).findAll();
  }

  @Test
  void searchShouldCallRepositoryWithPartialNameAndOptionalCriteria() {
    ObjectEntity o1 = new ObjectEntity();
    o1.setName("Perceuse");
    o1.setCategory(ObjectCategories.TOOLS);

    ObjectSearchDto searchDto = new ObjectSearchDto();
    searchDto.setName("Per");
    searchDto.setCategory(ObjectCategories.TOOLS);

    when(objectRepository.search("Per", null, ObjectCategories.TOOLS, null))
        .thenReturn(Arrays.asList(o1));

    List<ObjectEntity> result = objectService.search(searchDto);

    assertEquals(1, result.size());
    verify(objectRepository, times(1)).search("Per", null, ObjectCategories.TOOLS, null);
  }

  @Test
  void searchShouldAllowSearchingWithOnlyOneCriterion() {
    ObjectEntity o1 = new ObjectEntity();
    o1.setName("Marteau");

    ObjectSearchDto searchDto = new ObjectSearchDto();
    searchDto.setName("Mar");

    when(objectRepository.search("Mar", null, null, null)).thenReturn(Arrays.asList(o1));

    List<ObjectEntity> result = objectService.search(searchDto);

    assertEquals(1, result.size());
    verify(objectRepository, times(1)).search("Mar", null, null, null);
  }

  @Test
  void searchShouldTrimEmptyNameToNull() {
    ObjectEntity o1 = new ObjectEntity();
    ObjectSearchDto searchDto = new ObjectSearchDto();
    searchDto.setName("   ");
    searchDto.setStateOfWear(ObjectStateOfWear.NEW);

    when(objectRepository.search(null, ObjectStateOfWear.NEW, null, null))
        .thenReturn(Arrays.asList(o1));

    List<ObjectEntity> result = objectService.search(searchDto);

    assertEquals(1, result.size());
    verify(objectRepository, times(1)).search(null, ObjectStateOfWear.NEW, null, null);
  }

  @Test
  void searchShouldCallRepositoryWithAllCriteriaWhenProvided() {
    ObjectEntity o1 = new ObjectEntity();
    o1.setName("Perceuse");
    o1.setCategory(ObjectCategories.TOOLS);
    o1.setStateOfWear(ObjectStateOfWear.NEW);
    o1.setMaterial(ObjectMaterial.METAL);

    ObjectSearchDto searchDto = new ObjectSearchDto();
    searchDto.setName("Perceuse");
    searchDto.setCategory(ObjectCategories.TOOLS);
    searchDto.setStateOfWear(ObjectStateOfWear.NEW);
    searchDto.setMaterial(ObjectMaterial.METAL);

    when(objectRepository.search(
            "Perceuse", ObjectStateOfWear.NEW, ObjectCategories.TOOLS, ObjectMaterial.METAL))
        .thenReturn(Arrays.asList(o1));

    List<ObjectEntity> result = objectService.search(searchDto);

    assertEquals(1, result.size());
    verify(objectRepository, times(1))
        .search("Perceuse", ObjectStateOfWear.NEW, ObjectCategories.TOOLS, ObjectMaterial.METAL);
  }
}
