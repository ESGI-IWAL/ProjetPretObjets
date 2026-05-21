package com.aliw.pretemoica.service;

import com.aliw.pretemoica.dto.SearchObjectDto;
import com.aliw.pretemoica.entity.ObjectEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public final class ObjectSpecifications {

  private ObjectSpecifications() {}

  public static Specification<ObjectEntity> fromSearchDto(SearchObjectDto dto) {
    return (Root<ObjectEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
      Predicate predicate = cb.conjunction();

      if (dto.getCategory() != null) {
        predicate = cb.and(predicate, cb.equal(root.get("category"), dto.getCategory()));
      }
      if (dto.getState() != null) {
        predicate = cb.and(predicate, cb.equal(root.get("stateOfWear"), dto.getState()));
      }
      if (dto.getMaterial() != null) {
        predicate = cb.and(predicate, cb.equal(root.get("material"), dto.getMaterial()));
      }

      return predicate;
    };
  }
}
