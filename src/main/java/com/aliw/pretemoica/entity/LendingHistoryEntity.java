package com.aliw.pretemoica.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
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
     * @return true s'il y a une erreur (début après fin)
     */
    public boolean hasDateError() {
        if (startedAt == null || endedAt == null) return false;
        return startedAt.isAfter(endedAt);
    }


    public Long getId() { return id; }

    public ObjectEntity getObject() { return object; }
    public void setObject(ObjectEntity object) { this.object = object; }

    public UserEntity getOfferedBy() { return offeredBy; }
    public void setOfferedBy(UserEntity offeredBy) { this.offeredBy = offeredBy; }

    public UserEntity getBorrowedBy() { return borrowedBy; }
    public void setBorrowedBy(UserEntity borrowedBy) { this.borrowedBy = borrowedBy; }

    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }

    public LocalDateTime getEndedAt() { return endedAt; }
    public void setEndedAt(LocalDateTime endedAt) { this.endedAt = endedAt; }
}