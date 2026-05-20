package com.aliw.pretemoica.service;

import com.aliw.pretemoica.dto.CreateLendingDto;
import com.aliw.pretemoica.dto.UpdateLendingDto;
import com.aliw.pretemoica.entity.LendingEntity;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.UserEntity;
import com.aliw.pretemoica.exception.ResourceNotFoundException;
import com.aliw.pretemoica.mapper.LendingMapper;
import com.aliw.pretemoica.repository.LendingRepository;
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

    if (lendingDto.getStartDate() != null) {
      lendingEntity.setStartedAt(LendingMapper.parseDateTime(lendingDto.getStartDate()));
    }

    if (lendingDto.getEndDate() != null) {
      lendingEntity.setEndedAt(LendingMapper.parseDateTime(lendingDto.getEndDate()));
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
