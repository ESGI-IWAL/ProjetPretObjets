package com.aliw.pretemoica.dto;

import com.aliw.pretemoica.entity.ObjectEntity.ObjectCategories;
import com.aliw.pretemoica.entity.ObjectEntity.ObjectMaterial;
import com.aliw.pretemoica.entity.ObjectEntity.ObjectStateOfWear;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateObjectDto {

  private String name;

  private String description;

  private ObjectCategories category;

  private Double weight;

  private String dimensions;

  private ObjectStateOfWear state;

  private ObjectMaterial material;
}
