package com.aliw.pretemoica.service;

import com.aliw.pretemoica.dto.LendingSearchDto;
import com.aliw.pretemoica.dto.ObjectInfoDisponibilityDto;
import com.aliw.pretemoica.dto.SearchLendingWithIdsObjectsDto;
import com.aliw.pretemoica.entity.LendingEntity;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.repository.LendingRepository;
import com.aliw.pretemoica.repository.ObjectRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LendingService {

  private final LendingRepository lendingRepository;
  private final ObjectRepository objectRepository;

  public LendingService(LendingRepository lendingRepository, ObjectRepository objectRepository) {
    this.lendingRepository = lendingRepository;
    this.objectRepository = objectRepository;
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

    String statusStr = null;
    if (searchDto.getStatus() != null) {
      statusStr = searchDto.getStatus().toString();
    }

    return lendingRepository.search(
        normalize(searchDto.getObjectName()),
        normalize(searchDto.getBorrowerName()),
        searchDto.getStartAt(),
        searchDto.getEndAt(),
        statusStr);
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

  public List<ObjectInfoDisponibilityDto> searchObjectsDisponibility(
      SearchLendingWithIdsObjectsDto searchDto) {
    // Si recherche null ou pas d'IDs fournis, retourner tous les objets
    if (searchDto == null
        || searchDto.getIdsObject() == null
        || searchDto.getIdsObject().isEmpty()) {
      // Récupère tous les objets
      List<Long> allObjectIds =
          objectRepository.findAll().stream().map(obj -> obj.getId()).toList();

      if (allObjectIds.isEmpty()) {
        return List.of();
      }

      // Récupère tous les prêts
      List<LendingEntity> lendings = lendingRepository.findAllLendingsGroupedByObject();

      // Utilise les mêmes IDs que le getAll des objets
      searchDto = new SearchLendingWithIdsObjectsDto();
      searchDto.setIdsObject(allObjectIds);

      return buildObjectsDisponibility(allObjectIds, lendings);
    }

    // Récupère les prêts pour les IDs d'objets demandés
    List<LendingEntity> lendings =
        lendingRepository.findLendingsForObjects(searchDto.getIdsObject());

    return buildObjectsDisponibility(searchDto.getIdsObject(), lendings);
  }

  private List<ObjectInfoDisponibilityDto> buildObjectsDisponibility(
      List<Long> objectIds, List<LendingEntity> lendings) {
    // Construit la réponse groupée par objet
    return objectIds.stream()
        .map(
            objectId -> {
              List<LendingEntity> objectLendings =
                  lendings.stream()
                      .filter(l -> l.getObject().getId().equals(objectId))
                      .sorted(Comparator.comparing(LendingEntity::getStartedAt).reversed())
                      .toList();

              ObjectInfoDisponibilityDto dto = new ObjectInfoDisponibilityDto();
              dto.setId(objectId);

              if (!objectLendings.isEmpty()) {
                // Le prêt courant (le plus récent avec startedAt)
                LendingEntity currentLending = objectLendings.get(0);
                dto.setEndCurrentLending(currentLending.getEndedAt());

                // Le prochain prêt (le 2ème le plus récent, ou null si pas de suivant)
                if (objectLendings.size() > 1) {
                  dto.setNextLending(objectLendings.get(1).getStartedAt());
                }
              }

              return dto;
            })
        .toList();
  }

  public List<LendingEntity> searchLendingsByObjectsAndDates(
      SearchLendingWithIdsObjectsDto searchDto) {

    // Si aucun paramètre, retourner tous les lendings
    if (searchDto == null
        || searchDto.getIdsObject() == null
        || searchDto.getIdsObject().isEmpty()) {
      return lendingRepository.findAll();
    }

    // Si les deux dates sont nulles, retourner tous les lendings pour ces objets
    if (searchDto.getDisponibilityStartDate() == null
        && searchDto.getDisponibilityEndDate() == null) {
      return lendingRepository.findByObjectIdIn(searchDto.getIdsObject());
    }

    // Sinon, filtrer par dates
    return lendingRepository.findByObjectIdInAndDates(
        searchDto.getIdsObject(),
        searchDto.getDisponibilityStartDate(),
        searchDto.getDisponibilityEndDate());
  }
}
