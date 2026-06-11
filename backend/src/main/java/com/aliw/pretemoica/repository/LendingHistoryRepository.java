package com.aliw.pretemoica.repository;

import com.aliw.pretemoica.entity.LendingHistoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LendingHistoryRepository extends JpaRepository<LendingHistoryEntity, Long> {

  @Transactional
  void deleteAllByObjectId(Long objectId);
}
