package com.aliw.pretemoica.service;

import com.aliw.pretemoica.dto.CreateLendingDto;
import com.aliw.pretemoica.dto.LendingPeriodDto;
import com.aliw.pretemoica.dto.LendingSearchDto;
import com.aliw.pretemoica.dto.ObjectInfoDisponibilityDto;
import com.aliw.pretemoica.dto.SearchLendingWithIdsObjectsDto;
import com.aliw.pretemoica.dto.UpdateLendingDto;
import com.aliw.pretemoica.entity.LendingEntity;
import com.aliw.pretemoica.entity.LendingStatus;
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
  private final ObjectRepository objectRepository;
  private final ObjectService objectService;
  private final UserService userService;

  public LendingService(
      LendingRepository lendingRepository,
      ObjectRepository objectRepository,
      ObjectService objectService,
      UserService userService) {
    this.lendingRepository = lendingRepository;
    this.objectRepository = objectRepository;
    this.objectService = objectService;
    this.userService = userService;
  }

  public LendingEntity create(LendingEntity lendingEntity) {
    // Assurer le statut par défaut lors de la création
    if (lendingEntity.getStatus() == null) {
      lendingEntity.setStatus(LendingStatus.PENDING);
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
      LendingStatus newStatus = parseStatus(lendingDto.getStatus());
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
          objectRepository.findAll().stream().map(ObjectEntity::getId).toList();

      if (allObjectIds.isEmpty()) {
        return List.of();
      }

      // Récupère tous les prêts
      List<LendingEntity> lendings = lendingRepository.findAllLendingsGroupedByObject();

      // Utilise les mêmes IDs que le getAll des objets
      searchDto = new SearchLendingWithIdsObjectsDto();
      searchDto.setIdsObject(allObjectIds);

      return buildObjectsDisponibility(allObjectIds, lendings, searchDto.getDisponibilityDate());
    }

    // Récupère les prêts pour les IDs d'objets demandés
    List<LendingEntity> lendings =
        lendingRepository.findLendingsForObjects(searchDto.getIdsObject());

    return buildObjectsDisponibility(
        searchDto.getIdsObject(), lendings, searchDto.getDisponibilityDate());
  }

  private List<ObjectInfoDisponibilityDto> buildObjectsDisponibility(
      List<Long> objectIds, List<LendingEntity> lendings, java.time.LocalDate referenceDate) {
    // Construit la réponse groupée par objet
    return objectIds.stream()
        .map(
            objectId -> {
              List<LendingEntity> objectLendings =
                  lendings.stream()
                      .filter(l -> l.getObject().getId().equals(objectId))
                      .sorted(Comparator.comparing(LendingEntity::getStartedAt))
                      .toList();

              ObjectInfoDisponibilityDto dto = new ObjectInfoDisponibilityDto();
              dto.setId(objectId);

              // Trouver le prêt actif à la date donnée
              LendingEntity activeLending = null;
              for (LendingEntity lending : objectLendings) {
                if (isLendingActiveOnDate(lending, referenceDate)) {
                  activeLending = lending;
                  break;
                }
              }

              if (activeLending != null) {
                // Il y a un prêt actif à cette date
                if (activeLending.getEndedAt() != null) {
                  dto.setEndCurrentLending(activeLending.getEndedAt());
                } else {
                  dto.setCurrentLendingStart(activeLending.getStartedAt());
                }

                // Trouver le prochain prêt uniquement si le prêt actif a une date de fin
                if (activeLending.getEndedAt() != null) {
                  java.time.LocalDateTime activeLendingEnd = activeLending.getEndedAt();
                  for (LendingEntity lending : objectLendings) {
                    if (lending.getStartedAt().isAfter(activeLendingEnd)) {
                      dto.setNextLending(lending.getStartedAt());
                      break;
                    }
                  }
                }
              } else if (referenceDate != null) {
                // Si l'objet est libre à la date de référence, renvoyer le prochain prêt futur
                java.time.LocalDateTime referenceDateTime = referenceDate.atStartOfDay();
                for (LendingEntity lending : objectLendings) {
                  if (lending.getStartedAt().isAfter(referenceDateTime)) {
                    dto.setNextLending(lending.getStartedAt());
                    break;
                  }
                }
              }

              return dto;
            })
        .toList();
  }

  /**
   * Vérifie si un prêt est actif à une date donnée.
   *
   * <p>Règles: 1. Si le prêt a une date de fin: la date de fin doit être >= à la date donnée 2. La
   * date de début du prêt doit être <= à la date donnée
   *
   * @param lending le prêt à vérifier
   * @param date la date à vérifier (peut être null)
   * @return true si le prêt est actif à cette date
   */
  private boolean isLendingActiveOnDate(LendingEntity lending, java.time.LocalDate date) {
    if (lending == null || lending.getStartedAt() == null) {
      return false;
    }

    // Convertir la LocalDate en LocalDateTime pour comparaison
    java.time.LocalDateTime lendingStart = lending.getStartedAt();
    java.time.LocalDateTime lendingEnd = lending.getEndedAt();

    // Si aucune date de référence, considérer le prêt comme actif
    if (date == null) {
      return true;
    }

    java.time.LocalDateTime refDateTime = date.atStartOfDay();

    // Le prêt est actif si:
    // 1. La date de début du prêt <= la date donnée
    // 2. Et (la date de fin du prêt est null OU la date de fin du prêt >= la date donnée)
    return !lendingStart.isAfter(refDateTime)
        && (lendingEnd == null || !lendingEnd.isBefore(refDateTime));
  }

  public List<LendingEntity> searchLendingsByObjectsAndDates(
      SearchLendingWithIdsObjectsDto searchDto) {

    // Si aucun paramètre, retourner tous les lendings
    if (searchDto == null
        || searchDto.getIdsObject() == null
        || searchDto.getIdsObject().isEmpty()) {
      return lendingRepository.findAll();
    }

    // Si la date est nulle, retourner tous les lendings pour ces objets
    if (searchDto.getDisponibilityDate() == null) {
      return lendingRepository.findByObjectIdIn(searchDto.getIdsObject());
    }

    // Sinon, filtrer par date
    return lendingRepository.findByObjectIdInAndDates(
        searchDto.getIdsObject(),
        searchDto.getDisponibilityDate(),
        searchDto.getDisponibilityDate());
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

    LendingStatus current = lending.getStatus();
    boolean changed = false;

    java.time.LocalDate today = java.time.LocalDate.now();

    // Si le prêt est VALIDATED et que la date de début arrive -> IN_PROGRESS
    if (current == LendingStatus.VALIDATED
        && lending.getStartedAt() != null
        && !today.isBefore(lending.getStartedAt().toLocalDate())) {
      lending.setStatus(LendingStatus.IN_PROGRESS);
      changed = true;
    }

    // Si le prêt est IN_PROGRESS et que la date de fin arrive -> COMPLETED
    if (current == LendingStatus.IN_PROGRESS
        && lending.getEndedAt() != null
        && !today.isBefore(lending.getEndedAt().toLocalDate())) {
      lending.setStatus(LendingStatus.COMPLETED);
      changed = true;
    }

    if (changed) {
      return lendingRepository.save(lending);
    }

    return null;
  }

  /** Permet de changer explicitement le statut en respectant les transitions autorisées. */
  public void changeStatus(Long id, LendingStatus newStatus) {
    LendingEntity entity = getById(id);
    changeStatus(entity, newStatus);
  }

  private void changeStatus(LendingEntity entity, LendingStatus newStatus) {
    LendingStatus current = entity.getStatus();

    if (current == newStatus) return; // Pas de changement

    validateTransition(current, newStatus);
    entity.setStatus(newStatus);
  }

  private LendingStatus parseStatus(String value) {
    if (value == null) return null;
    for (LendingStatus s : LendingStatus.values()) {
      if (s.getValue().equalsIgnoreCase(value)) return s;
    }
    throw new IllegalArgumentException("Statut inconnu: " + value);
  }

  private void validateTransition(LendingStatus current, LendingStatus newStatus) {
    if (current == newStatus) return;
    switch (current) {
      case PENDING:
        if (newStatus != LendingStatus.VALIDATED && newStatus != LendingStatus.REFUSED) {
          throw new IllegalArgumentException(
              "Depuis PENDING seul VALIDATED ou REFUSED sont autorisés");
        }
        break;
      case VALIDATED:
        if (newStatus != LendingStatus.IN_PROGRESS && newStatus != LendingStatus.CANCELED) {
          throw new IllegalArgumentException(
              "Depuis VALIDATED seul IN_PROGRESS (automatique) ou CANCELED sont autorisés");
        }
        break;
      case REFUSED:
        if (newStatus != LendingStatus.CANCELED) {
          throw new IllegalArgumentException("Depuis REFUSED seul CANCELED est autorisé");
        }
        break;
      case IN_PROGRESS:
        if (newStatus != LendingStatus.COMPLETED && newStatus != LendingStatus.CANCELED) {
          throw new IllegalArgumentException(
              "Depuis IN_PROGRESS seul COMPLETED (automatique) ou CANCELED sont autorisés");
        }
        break;
      case COMPLETED:
      case CANCELED:
        throw new IllegalArgumentException("Aucune transition autorisée depuis un état terminal");
    }
  }

  /**
   * Récupère les périodes de prêt (date de début et de fin) pour un objet, à partir de la date du
   * jour. Filtre uniquement les prêts avec une date de fin égale ou après la date d'aujourd'hui.
   *
   * @param objectId l'identifiant de l'objet
   * @return liste des périodes de prêt
   * @throws ResourceNotFoundException si l'objet n'existe pas
   */
  public List<LendingPeriodDto> getLendingPeriodsForObject(Long objectId) {
    // Vérifier que l'objet existe
    objectService.getById(objectId);

    java.time.LocalDate today = java.time.LocalDate.now();
    java.time.LocalDateTime todayStart = today.atStartOfDay();

    // Récupérer tous les lendings pour cet objet
    List<LendingEntity> lendings = lendingRepository.findByObjectIdIn(List.of(objectId));

    // Filtrer les lendings à partir d'aujourd'hui et mapper vers LendingPeriodDto
    return lendings.stream()
        .filter(
            lending -> lending.getEndedAt() != null && !lending.getEndedAt().isBefore(todayStart))
        .map(lending -> new LendingPeriodDto(lending.getStartedAt(), lending.getEndedAt()))
        .toList();
  }
}
