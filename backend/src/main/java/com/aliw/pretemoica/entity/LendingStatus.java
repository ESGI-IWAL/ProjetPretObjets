package com.aliw.pretemoica.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LendingStatus {
  IN_PROGRESS("in_progress"),
  PENDING("pending"),
  VALIDATED("validated"),
  REFUSED("refused"),
  COMPLETED("completed"),
  CANCELED("canceled");

  private final String value;

  LendingStatus(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
