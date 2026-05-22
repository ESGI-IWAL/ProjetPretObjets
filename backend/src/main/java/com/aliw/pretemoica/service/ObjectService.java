package com.aliw.pretemoica.service;

import com.aliw.pretemoica.dto.CreateObjectDto;
import com.aliw.pretemoica.dto.ObjectDto;
import com.aliw.pretemoica.dto.ObjectSearchDto;
import com.aliw.pretemoica.dto.UpdateObjectDto;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.mapper.ObjectMapper;
import com.aliw.pretemoica.repository.ObjectRepository;
import com.aliw.pretemoica.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ObjectService {

  private final ObjectRepository objectRepository;
  private final UserRepository userRepository;

  public ObjectService(ObjectRepository objectRepository, UserRepository userRepository) {
    this.objectRepository = objectRepository;
    this.userRepository = userRepository;
  }

  public ObjectEntity create(ObjectEntity objectEntity) {
    return objectRepository.save(objectEntity);
  }

  public ObjectDto create(CreateObjectDto createObjectDto, Long ownedById) {
    ObjectEntity entity = ObjectMapper.toEntityFromCreate(createObjectDto);

    entity.setOwnedBy(
        userRepository
            .findById(ownedById)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Utilisateur introuvable avec l'id: " + ownedById)));

    ObjectEntity savedEntity = objectRepository.save(entity);
    return ObjectMapper.toDto(savedEntity);
  }

  public List<ObjectEntity> getAll() {
    return objectRepository.findAll();
  }

  public List<ObjectEntity> search(ObjectSearchDto searchDto) {
    if (searchDto == null) {
      return getAll();
    }

    return objectRepository.search(
        normalize(searchDto.getName()),
        searchDto.getStateOfWear(),
        searchDto.getCategory(),
        searchDto.getMaterial());
  }

  public ObjectEntity getById(Long id) {
    return objectRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Objet introuvable avec l'id: " + id));
  }

  public void delete(Long id) {
    ObjectEntity objectEntity = getById(id);
    objectRepository.delete(objectEntity);
  }

  public ObjectDto update(Long id, UpdateObjectDto updateObjectDto) {
    ObjectEntity objectEntity = getById(id);
    ObjectEntity updatedEntity = ObjectMapper.toEntityFromUpdate(updateObjectDto, objectEntity);
    ObjectEntity savedEntity = objectRepository.save(updatedEntity);
    return ObjectMapper.toDto(savedEntity);
  }

  private String normalize(String value) {
    if (value == null) {
      return null;
    }

    String trimmed = value.trim();
    return trimmed.isEmpty() ? null : trimmed;
  }
}
