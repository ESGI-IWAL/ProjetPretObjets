package com.aliw.pretemoica;

import jakarta.persistence.*;



@Entity
public class ObjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owned_by_id", nullable = false)
    private UserEntity ownedBy;

    @Enumerated(EnumType.STRING)
    private ObjectStatus status = ObjectStatus.AVAILABLE; // Valeur par défaut

    @Enumerated(EnumType.STRING)
    private ObjectState state;

    public ObjectEntity() {
        //En attente
    }

    public enum ObjectStatus {
        AVAILABLE,   // Disponible au prêt
        LENT,        // Actuellement prêté
        RESERVED,    // Réservé par quelqu'un
        UNAVAILABLE  // Retiré temporairement par le propriétaire
    }

    public enum ObjectState {
        NEW,         // Neuf
        GOOD,        // Bon état
        WORN,        // Usagé
        DAMAGED      // Abîmé
    }

    // --- Getters et Setters ---

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public UserEntity getOwnedBy() { return ownedBy; }
    public void setOwnedBy(UserEntity ownedBy) { this.ownedBy = ownedBy; }

    public ObjectStatus getStatus() { return status; }
    public void setStatus(ObjectStatus status) { this.status = status; }

    public ObjectState getState() { return state; }
    public void setState(ObjectState state) { this.state = state; }
}