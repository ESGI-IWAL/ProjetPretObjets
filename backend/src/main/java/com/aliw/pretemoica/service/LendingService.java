package com.aliw.pretemoica.service;

import com.aliw.pretemoica.dto.LendingSearchDto;
import com.aliw.pretemoica.dto.ObjectInfoDisponibilityDto;
import com.aliw.pretemoica.dto.SearchLendingWithIdsObjectsDto;
import com.aliw.pretemoica.dto.CreateLendingDto;
import com.aliw.pretemoica.dto.UpdateLendingDto;
import com.aliw.pretemoica.entity.LendingEntity;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.UserEntity;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.mapper.LendingMapper;
import com.aliw.pretemoica.repository.LendingRepository;
import com.aliw.pretemoica.repository.ObjectRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LendingService {

  private final LendingRepository lendingRepository;
  private final ObjectService objectService;
  private final UserService userService;

  public LendingService(
      LendingRepository lendingRepository, ObjectService objectService, UserService userService) {
    this.lendingRepository = lendingRepository;
    this.objectService = objectService;
    this.userService = userService;
  }

  public LendingEntity create(LendingEntity lendingEntity) {
    // Assurer le statut par défaut lors de la création
    if (lendingEntity.getStatus() == null) {
      lendingEntity.setStatus(LendingEntity.LendingStatus.PENDING);
    }
    return lendingRepository.save(lendingEntity);
  }

  public LendingEntity create(CreateLendingDto lendingDto) {
    LendingEntity lendingEntity = LendingMapper.toEntity(lendingDto);

    lendingEntity.setBorrowedBy(userService.getById(requiredId(lendingEntity.getBorrowedBy())));

    ObjectEntity lendingObject = objectService.getById(requiredId(lendingEntity.getObject()));
    lendingEntity.setObject(lendingObject);
    lendingEntity.setOfferedBy(resolveOwner(lendingObject));

    return create(lendingEntity);
  }

  public LendingEntity update(Long id, UpdateLendingDto lendingDto) {
    if (lendingDto == null) {
      throw new IllegalArgumentException("Le corps de la requête est obligatoire");
    }

    LendingEntity lendingEntity = getById(id);

    // Modification des identifiants d'objet et d'emprunteur interdite via l'endpoint update
    // Les champs borrowerId et objectId dans UpdateLendingDto sont ignorés intentionnellement.

    if (lendingDto.getStartAt() != null) {
      lendingEntity.setStartedAt(LendingMapper.parseDateTime(lendingDto.getStartAt()));
    }

    if (lendingDto.getEndAt() != null) {
      lendingEntity.setEndedAt(LendingMapper.parseDateTime(lendingDto.getEndAt()));
    }

    // Gestion du changement de statut via l'endpoint update
    if (lendingDto.getStatus() != null) {
      LendingEntity.LendingStatus newStatus = parseStatus(lendingDto.getStatus());
      changeStatus(lendingEntity, newStatus);
    }

    return lendingRepository.save(lendingEntity);
  }

  public List<LendingEntity> getAll() {
    List<LendingEntity> all = lendingRepository.findAll();
    // Rafraîchit le statut basé sur les dates et persiste si nécessaire
    for (int i = 0; i < all.size(); i++) {
      LendingEntity l = all.get(i);
      LendingEntity updated = refreshStatusIfNeeded(l);
      if (updated != null) {
        all.set(i, updated);
      }
    }
    return all;
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
    LendingEntity entity =
        lendingRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Prêt introuvable avec l'id: " + id));

    LendingEntity updated = refreshStatusIfNeeded(entity);
    return updated != null ? updated : entity;
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
  private Long requiredId(UserEntity entity) {
    if (entity == null || entity.getId() == null) {
      throw new IllegalArgumentException("Le champ borrowerId est obligatoire");
    }
    return entity.getId();
  }

  private Long requiredId(ObjectEntity entity) {
    if (entity == null || entity.getId() == null) {
      throw new IllegalArgumentException("Le champ objectId est obligatoire");
    }
    return entity.getId();
  }

  private UserEntity resolveOwner(ObjectEntity lendingObject) {
    if (lendingObject.getOwnedBy() == null) {
      throw new ResourceNotFoundException(
          "Propriétaire introuvable pour l'objet: " + lendingObject.getId());
    }
    return lendingObject.getOwnedBy();
  }

  /**
   * Met à jour le statut d'un prêt en fonction des règles métiers et des dates. Retourne l'entité
   * sauvegardée si un changement a eu lieu, sinon null.
   */
  private LendingEntity refreshStatusIfNeeded(LendingEntity lending) {
    if (lending == null) return null;

    LendingEntity.LendingStatus current = lending.getStatus();
    boolean changed = false;

    java.time.LocalDate today = java.time.LocalDate.now();

    // Si le prêt est VALIDATED et que la date de début arrive -> IN_PROGRESS
    if (current == LendingEntity.LendingStatus.VALIDATED
        && lending.getStartedAt() != null
        && today.isEqual(lending.getStartedAt().toLocalDate())) {
      lending.setStatus(LendingEntity.LendingStatus.IN_PROGRESS);
      changed = true;
    }

    // Si le prêt est IN_PROGRESS et que la date de fin arrive -> COMPLETED
    if (current == LendingEntity.LendingStatus.IN_PROGRESS
        && lending.getEndedAt() != null
        && today.isEqual(lending.getEndedAt().toLocalDate())) {
      lending.setStatus(LendingEntity.LendingStatus.COMPLETED);
      changed = true;
    }

    if (changed) {
      return lendingRepository.save(lending);
    }

    return null;
  }

  /** Permet de changer explicitement le statut en respectant les transitions autorisées. */
  public void changeStatus(Long id, LendingEntity.LendingStatus newStatus) {
    LendingEntity entity = getById(id);
    changeStatus(entity, newStatus);
  }

  private void changeStatus(LendingEntity entity, LendingEntity.LendingStatus newStatus) {
    LendingEntity.LendingStatus current = entity.getStatus();

    if (current == newStatus) return; // Pas de changement

    validateTransition(current, newStatus);
    entity.setStatus(newStatus);
  }

  private LendingEntity.LendingStatus parseStatus(String value) {
    if (value == null) return null;
    for (LendingEntity.LendingStatus s : LendingEntity.LendingStatus.values()) {
      if (s.getValue().equalsIgnoreCase(value)) return s;
    }
    throw new IllegalArgumentException("Statut inconnu: " + value);
  }

  private void validateTransition(
      LendingEntity.LendingStatus current, LendingEntity.LendingStatus newStatus) {
    if (current == newStatus) return;
    switch (current) {
      case PENDING:
        if (newStatus != LendingEntity.LendingStatus.VALIDATED
            && newStatus != LendingEntity.LendingStatus.REFUSED) {
          throw new IllegalArgumentException(
              "Depuis PENDING seul VALIDATED ou REFUSED sont autorisés");
        }
        break;
      case VALIDATED:
        if (newStatus != LendingEntity.LendingStatus.IN_PROGRESS
            && newStatus != LendingEntity.LendingStatus.CANCELED) {
          throw new IllegalArgumentException(
              "Depuis VALIDATED seul IN_PROGRESS (automatique) ou CANCELED sont autorisés");
        }
        break;
      case REFUSED:
        if (newStatus != LendingEntity.LendingStatus.CANCELED) {
          throw new IllegalArgumentException("Depuis REFUSED seul CANCELED est autorisé");
        }
        break;
      case IN_PROGRESS:
        if (newStatus != LendingEntity.LendingStatus.COMPLETED
            && newStatus != LendingEntity.LendingStatus.CANCELED) {
          throw new IllegalArgumentException(
              "Depuis IN_PROGRESS seul COMPLETED (automatique) ou CANCELED sont autorisés");
        }
        break;
      case COMPLETED:
      case CANCELED:
        throw new IllegalArgumentException("Aucune transition autorisée depuis un état terminal");
    }
  }
}
