package com.aliw.pretemoica.dto;

import com.aliw.pretemoica.entity.ObjectEntity.ObjectCategories;
import com.aliw.pretemoica.entity.ObjectEntity.ObjectMaterial;
import com.aliw.pretemoica.entity.ObjectEntity.ObjectStateOfWear;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchObjectDto {

  private ObjectCategories category;
  private ObjectStateOfWear state;
  private ObjectMaterial material;
  private LocalDateTime disponibilityStartDate;
  private LocalDateTime disponibilityEndDate;
}
