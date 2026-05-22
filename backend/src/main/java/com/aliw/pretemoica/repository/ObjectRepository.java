package com.aliw.pretemoica.repository;

import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.ObjectEntity.ObjectCategories;
import com.aliw.pretemoica.entity.ObjectEntity.ObjectMaterial;
import com.aliw.pretemoica.entity.ObjectEntity.ObjectStateOfWear;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectRepository extends JpaRepository<ObjectEntity, Long> {

  @Query("""
      select o
      from ObjectEntity o
      where (:name is null or lower(o.name) like lower(concat('%', :name, '%')))
      and (:stateOfWear is null or o.stateOfWear = :stateOfWear)
      and (:category is null or o.category = :category)
      and (:material is null or o.material = :material)
      order by o.name asc
      """)
  List<ObjectEntity> search(
      @Param("name") String name,
      @Param("stateOfWear") ObjectStateOfWear stateOfWear,
      @Param("category") ObjectCategories category,
      @Param("material") ObjectMaterial material);
}
