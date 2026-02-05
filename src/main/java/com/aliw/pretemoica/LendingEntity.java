package com.aliw.pretemoica;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LendingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Standard pour PostgreSQL
    private Long id;

    @ManyToOne
    @JoinColumn(name = "borrowed_by_id", nullable = false)
    private UserEntity borrowedBy;

    @ManyToOne
    @JoinColumn(name = "offered_by_id", nullable = false)
    private UserEntity offeredBy;

    // Changement : ManyToOne car un objet peut figurer dans plusieurs transactions de prêt
    @ManyToOne
    @JoinColumn(name = "object_id", nullable = false) // On lie par l'ID, pas par le nom
    private ObjectEntity object;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    public LendingEntity() {
        this.startedAt = LocalDateTime.now();
    }

    // --- Getters et Setters ---

    public Long getId() { return id; }

    public UserEntity getBorrowedBy() { return borrowedBy; }
    public void setBorrowedBy(UserEntity borrowedBy) { this.borrowedBy = borrowedBy; }

    public UserEntity getOfferedBy() { return offeredBy; }
    public void setOfferedBy(UserEntity offeredBy) { this.offeredBy = offeredBy; }

    public ObjectEntity getObject() { return object; }
    public void setObject(ObjectEntity object) { this.object = object; }

    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }

    public LocalDateTime getEndedAt() { return endedAt; }
    public void setEndedAt(LocalDateTime endedAt) { this.endedAt = endedAt; }
}