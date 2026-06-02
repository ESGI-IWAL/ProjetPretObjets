package com.aliw.pretemoica.service;

import com.aliw.pretemoica.dto.UserDto;
import com.aliw.pretemoica.entity.UserEntity;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.repository.UserRepository;
import java.util.List;
import java.util.Optional;
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

  public UserEntity getMyProfile(String identifier) {
    return findCurrentUser(identifier);
  }

  public UserEntity updateMyProfile(String identifier, UserDto userDto) {
    UserEntity currentUser = findCurrentUser(identifier);

    if (userDto.getUsername() != null && !userDto.getUsername().isBlank()) {
      currentUser.setUsername(userDto.getUsername());
    }

    if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
      currentUser.setEmail(userDto.getEmail());
    }

    if (userDto.getRating() != null) {
      currentUser.setRating(userDto.getRating());
    }

    return userRepository.save(currentUser);
  }

  private UserEntity findCurrentUser(String identifier) {
    if (identifier == null || identifier.isBlank()) {
      throw new ResourceNotFoundException("Utilisateur courant introuvable: identifiant manquant");
    }

    Optional<UserEntity> byEmail = userRepository.findByEmailIgnoreCase(identifier);
    if (byEmail.isPresent()) {
      return byEmail.get();
    }

    return userRepository
        .findByUsernameIgnoreCase(identifier)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "Utilisateur courant introuvable avec l'identifiant: " + identifier));
  }
}
