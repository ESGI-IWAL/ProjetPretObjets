package com.aliw.pretemoica.repository;

import com.aliw.pretemoica.entity.ObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectRepository
    extends JpaRepository<ObjectEntity, Long>, JpaSpecificationExecutor<ObjectEntity> {}
