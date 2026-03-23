package com.aliw.pretemoica.entity;

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
    private ObjectStatus status = ObjectStatus.AVAILABLE;

    @Enumerated(EnumType.STRING)
    private ObjectState state;

    public ObjectEntity() {
        //En attente
    }

    public enum ObjectStatus {
        AVAILABLE,
        LENT,
        RESERVED,
        UNAVAILABLE
    }

    public enum ObjectState {
        NEW,
        GOOD,
        WORN,
        DAMAGED
    }



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