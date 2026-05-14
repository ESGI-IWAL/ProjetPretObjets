package com.aliw.pretemoica.service;

import com.aliw.pretemoica.entity.LendingHistoryEntity;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LendingHistoryService {

  private final com.aliw.pretemoica.repository.LendingHistoryRepository lendingHistoryRepository;

  public LendingHistoryService(
      com.aliw.pretemoica.repository.LendingHistoryRepository lendingHistoryRepository) {
    this.lendingHistoryRepository = lendingHistoryRepository;
  }

  public LendingHistoryEntity create(LendingHistoryEntity lendingHistoryEntity) {
    return lendingHistoryRepository.save(lendingHistoryEntity);
  }

  public List<LendingHistoryEntity> getAll() {
    return lendingHistoryRepository.findAll();
  }

  public LendingHistoryEntity getById(Long id) {
    return lendingHistoryRepository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("Historique de prêt introuvable avec l'id: " + id));
  }

  public void delete(Long id) {
    LendingHistoryEntity lendingHistoryEntity = getById(id);
    lendingHistoryRepository.delete(lendingHistoryEntity);
  }
}
