package com.aliw.pretemoica.service;

import com.aliw.pretemoica.dto.CreateLendingDto;
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
}
