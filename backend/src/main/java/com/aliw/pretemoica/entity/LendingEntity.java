package com.aliw.pretemoica.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "lending")
public class LendingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "borrowed_by_id", nullable = false)
  private UserEntity borrowedBy;

  @ManyToOne
  @JoinColumn(name = "offered_by_id", nullable = false)
  private UserEntity offeredBy;

  @ManyToOne
  @JoinColumn(name = "object_id", nullable = false)
  private ObjectEntity object;

  private LocalDateTime startedAt;
  private LocalDateTime endedAt;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private LendingStatus status;

  public LendingEntity() {
    this.startedAt = LocalDateTime.now();
    this.status = LendingStatus.PENDING;
  }

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
}
