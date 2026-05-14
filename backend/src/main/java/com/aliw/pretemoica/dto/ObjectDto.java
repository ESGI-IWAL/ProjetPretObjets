package com.aliw.pretemoica.dto;

import com.aliw.pretemoica.entity.ObjectEntity.ObjectState;
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
  private Long ownedById;
  private ObjectStatus status;
  private ObjectState state;
}
