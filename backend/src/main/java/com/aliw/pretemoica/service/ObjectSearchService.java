package com.aliw.pretemoica.service;

import com.aliw.pretemoica.dto.SearchObjectDto;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.repository.ObjectRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ObjectSearchService {

  private final ObjectRepository objectRepository;
  private final LendingService lendingService;

  public ObjectSearchService(ObjectRepository objectRepository, LendingService lendingService) {
    this.objectRepository = objectRepository;
    this.lendingService = lendingService;
  }

  public List<ObjectEntity> search(SearchObjectDto dto) {
    if (dto == null) {
      return objectRepository.findAll();
    }

    // Récupère les objets avec filtres category/state/material
    Specification<ObjectEntity> spec = ObjectSpecifications.fromSearchDto(dto);
    List<ObjectEntity> objects = objectRepository.findAll(spec);

    // Filtre par disponibilité en Java si les dates sont fournies
    LocalDateTime startDate = dto.getDisponibilityStartDate();
    LocalDateTime endDate = dto.getDisponibilityEndDate();

    if (startDate != null && endDate != null) {
      return objects.stream()
          .filter(obj -> lendingService.isObjectAvailable(obj.getId(), startDate, endDate))
          .collect(Collectors.toList());
    }

    return objects;
  }
}
