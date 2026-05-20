package com.aliw.pretemoica.repository;

import com.aliw.pretemoica.entity.LendingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LendingHistoryRepository extends JpaRepository<LendingHistoryEntity, Long> {
  // Repository JPA pour l'historique des prêts
}
