package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.aliw.pretemoica.entity.UserEntity;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.repository.UserRepository;
import com.aliw.pretemoica.service.UserService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserService userService;

  @Test
  public void createShouldDelegateToRepository() {
    UserEntity u = new UserEntity();
    u.setEmail("a@a.com");
    when(userRepository.save(u)).thenReturn(u);

    UserEntity result = userService.create(u);

    assertSame(u, result);
    verify(userRepository, times(1)).save(u);
  }

  @Test
  public void getAllShouldReturnListFromRepository() {
    UserEntity u1 = new UserEntity();
    UserEntity u2 = new UserEntity();
    when(userRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

    List<UserEntity> list = userService.getAll();

    assertEquals(2, list.size());
    verify(userRepository, times(1)).findAll();
  }

  @Test
  public void getByIdShouldReturnWhenFound() {
    UserEntity u = new UserEntity();
    u.setId(1L);
    when(userRepository.findById(1L)).thenReturn(Optional.of(u));

    UserEntity found = userService.getById(1L);

    assertEquals(u, found);
  }

  @Test
  public void getByIdShouldThrowWhenNotFound() {
    when(userRepository.findById(5L)).thenReturn(Optional.empty());

    ResourceNotFoundException ex =
        assertThrows(ResourceNotFoundException.class, () -> userService.getById(5L));
    assertTrue(ex.getMessage().contains("5"));
  }

  @Test
  public void deleteShouldCallRepositoryDelete() {
    UserEntity u = new UserEntity();
    u.setId(2L);
    when(userRepository.findById(2L)).thenReturn(Optional.of(u));

    userService.delete(2L);

    verify(userRepository, times(1)).delete(u);
  }
}
