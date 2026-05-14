package com.aliw.pretemoica.service;

import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.repository.ObjectRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ObjectService {

  private final ObjectRepository objectRepository;

  public ObjectService(ObjectRepository objectRepository) {
    this.objectRepository = objectRepository;
  }

  public ObjectEntity create(ObjectEntity objectEntity) {
    return objectRepository.save(objectEntity);
  }

  public List<ObjectEntity> getAll() {
    return objectRepository.findAll();
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
}
