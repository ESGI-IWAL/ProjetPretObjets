package com.aliw.pretemoica.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "lending_history")
public class LendingHistoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Cohérence avec Postgres
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

  public LendingHistoryEntity() {
    this.startedAt = LocalDateTime.now();
  }

  /**
   * Vérifie si la période de prêt est valide.
   *
   * @return true s'il y a une erreur (début après fin)
   */
  public boolean hasDateError() {
    if (startedAt == null || endedAt == null) return false;
    return startedAt.isAfter(endedAt);
  }
}
