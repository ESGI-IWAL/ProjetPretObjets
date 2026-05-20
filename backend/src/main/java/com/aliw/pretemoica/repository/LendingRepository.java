package com.aliw.pretemoica.repository;

import com.aliw.pretemoica.entity.LendingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LendingRepository extends JpaRepository<LendingEntity, Long> {}
