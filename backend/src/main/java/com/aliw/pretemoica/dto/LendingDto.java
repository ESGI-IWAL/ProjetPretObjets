package com.aliw.pretemoica.dto;

import com.aliw.pretemoica.entity.LendingStatus;
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
  private UserDto borrowedBy;
  private UserDto offeredBy;
  private ObjectDto object;
  private LocalDateTime startedAt;
  private LocalDateTime endedAt;
  private LendingStatus status;
}
