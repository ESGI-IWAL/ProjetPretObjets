package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.aliw.pretemoica.dto.UpdateObjectDto;
import com.aliw.pretemoica.entity.ObjectEntity;
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
public class ObjectServiceTest {

  @Mock private ObjectRepository objectRepository;

  @InjectMocks private ObjectService objectService;

  @Test
  public void createShouldDelegateToRepository() {
    ObjectEntity o = new ObjectEntity();
    o.setName("Obj");
    when(objectRepository.save(o)).thenReturn(o);

    ObjectEntity res = objectService.create(o);

    assertSame(o, res);
    verify(objectRepository, times(1)).save(o);
  }

  @Test
  public void getAllShouldReturnList() {
    ObjectEntity o1 = new ObjectEntity();
    ObjectEntity o2 = new ObjectEntity();
    when(objectRepository.findAll()).thenReturn(Arrays.asList(o1, o2));

    List<ObjectEntity> list = objectService.getAll();

    assertEquals(2, list.size());
    verify(objectRepository, times(1)).findAll();
  }

  @Test
  public void getByIdShouldReturnWhenFound() {
    ObjectEntity o = new ObjectEntity();
    o.setId(1L);
    when(objectRepository.findById(1L)).thenReturn(Optional.of(o));

    ObjectEntity found = objectService.getById(1L);

    assertEquals(o, found);
  }

  @Test
  public void getByIdShouldThrowWhenNotFound() {
    when(objectRepository.findById(9L)).thenReturn(Optional.empty());

    ResourceNotFoundException ex =
        assertThrows(ResourceNotFoundException.class, () -> objectService.getById(9L));
    assertTrue(ex.getMessage().contains("9"));
  }

  @Test
  public void deleteShouldCallRepositoryDelete() {
    ObjectEntity o = new ObjectEntity();
    o.setId(3L);
    when(objectRepository.findById(3L)).thenReturn(Optional.of(o));

    objectService.delete(3L);

    verify(objectRepository, times(1)).delete(o);
  }

  @Test
  public void updateShouldUpdateObjectWhenFound() {
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
  public void updateShouldThrowWhenObjectNotFound() {
    UpdateObjectDto updateDto = new UpdateObjectDto();
    when(objectRepository.findById(99L)).thenReturn(Optional.empty());

    ResourceNotFoundException ex =
        assertThrows(ResourceNotFoundException.class, () -> objectService.update(99L, updateDto));
    assertTrue(ex.getMessage().contains("99"));
  }
}
