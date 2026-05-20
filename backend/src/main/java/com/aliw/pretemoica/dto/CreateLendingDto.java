package com.aliw.pretemoica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLendingDto {

  private String objectId;
  private String borrowerId;
  private String startDate;
  private String endDate;
}
