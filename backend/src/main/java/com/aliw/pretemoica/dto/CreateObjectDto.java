package com.aliw.pretemoica.dto;

import com.aliw.pretemoica.entity.ObjectEntity.ObjectCategories;
import com.aliw.pretemoica.entity.ObjectEntity.ObjectMaterial;
import com.aliw.pretemoica.entity.ObjectEntity.ObjectStateOfWear;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateObjectDto {

  @NotBlank(message = "Le nom est obligatoire")
  private String name;

  @NotBlank(message = "La description est obligatoire")
  private String description;

  @NotNull(message = "La catégorie est obligatoire")
  private ObjectCategories category;

  private Double weight;

  private String dimensions;

  @NotNull(message = "L'état est obligatoire")
  private ObjectStateOfWear state;

  private ObjectMaterial material;
}
