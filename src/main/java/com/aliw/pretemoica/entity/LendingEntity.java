package com.aliw.pretemoica.entity;

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

  public LendingEntity() {
    this.startedAt = LocalDateTime.now();
  }
}
