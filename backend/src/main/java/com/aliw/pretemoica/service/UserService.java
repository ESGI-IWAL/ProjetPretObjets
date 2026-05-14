package com.aliw.pretemoica.service;

import com.aliw.pretemoica.entity.UserEntity;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserEntity create(UserEntity user) {
    return userRepository.save(user);
  }

  public List<UserEntity> getAll() {
    return userRepository.findAll();
  }

  public UserEntity getById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("Utilisateur introuvable avec l'id: " + id));
  }

  public void delete(Long id) {
    UserEntity user = getById(id);
    userRepository.delete(user);
  }
}
