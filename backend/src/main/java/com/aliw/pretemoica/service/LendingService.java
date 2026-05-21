package com.aliw.pretemoica.service;

import com.aliw.pretemoica.dto.LendingSearchDto;
import com.aliw.pretemoica.entity.LendingEntity;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.repository.LendingRepository;
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

  public List<LendingEntity> search(LendingSearchDto searchDto) {
    if (searchDto == null) {
      return lendingRepository.search(null, null, null, null, null);
    }

    return lendingRepository.search(
        normalize(searchDto.getObjectName()),
        normalize(searchDto.getBorrowerName()),
        searchDto.getStartAt(),
        searchDto.getEndAt(),
        searchDto.getStatus());
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

  private String normalize(String value) {
    if (value == null) {
      return null;
    }

    String trimmed = value.trim();
    return trimmed.isEmpty() ? null : trimmed;
  }
}
