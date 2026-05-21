package com.aliw.pretemoica.service;

import com.aliw.pretemoica.entity.LendingEntity;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.repository.LendingRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LendingService {

  private final LendingRepository lendingRepository;

  public LendingService(LendingRepository lendingRepository) {
    this.lendingRepository = lendingRepository;
  }

  public LendingEntity create(LendingEntity lendingEntity) {
    return lendingRepository.save(lendingEntity);
  }

  public List<LendingEntity> getAll() {
    return lendingRepository.findAll();
  }

  public LendingEntity getById(Long id) {
    return lendingRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Prêt introuvable avec l'id: " + id));
  }

  public void delete(Long id) {
    LendingEntity lendingEntity = getById(id);
    lendingRepository.delete(lendingEntity);
  }

  public boolean isObjectAvailable(Long objectId, LocalDateTime startDate, LocalDateTime endDate) {
    return !lendingRepository.existsOverlappingLending(objectId, startDate, endDate);
  }
}
