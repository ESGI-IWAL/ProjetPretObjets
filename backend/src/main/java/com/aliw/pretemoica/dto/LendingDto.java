package com.aliw.pretemoica.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LendingDto {

  private Long id;
  private Long borrowedById;
  private Long offeredById;
  private Long objectId;
  private LocalDateTime startAt;
  private LocalDateTime endAt;
}
