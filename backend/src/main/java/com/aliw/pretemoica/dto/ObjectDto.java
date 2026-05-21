package com.aliw.pretemoica.dto;

import com.aliw.pretemoica.entity.ObjectEntity.ObjectCategories;
import com.aliw.pretemoica.entity.ObjectEntity.ObjectMaterial;
import com.aliw.pretemoica.entity.ObjectEntity.ObjectStateOfWear;
import com.aliw.pretemoica.entity.ObjectEntity.ObjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObjectDto {

  private Long id;
  private String name;
  private String description;
  private Double weight;
  private String dimensions;
  private Long ownedById;
  private ObjectStatus status;
  private ObjectStateOfWear stateOfWear;
  private ObjectCategories category;
  private ObjectMaterial material;
}
