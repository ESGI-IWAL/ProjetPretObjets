package com.aliw.pretemoica.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "object")
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
  private EObjectStateOfWear stateOfWear;

  @Enumerated(EnumType.STRING)
  private EObjectCategories category;

  @Enumerated(EnumType.STRING)
  private EObjectMaterial material;

  public ObjectEntity() {
    // En attente
  }

  public enum ObjectStatus {
    AVAILABLE,
    LENT,
    RESERVED,
    UNAVAILABLE
  }

  public enum EObjectStateOfWear {
    NEW,
    GOOD,
    WORN,
    DAMAGED
  }

  public enum EObjectCategories {
    ELECTRONICS,
    FURNITURE,
    TOOLS,
    CLOTHING,
    BOOKS,
    TOYS,
    SPORTS,
    OTHERS
  }

  public enum EObjectMaterial {
    PLASTIC,
    METAL,
    WOOD,
    FABRIC,
    GLASS,
    CERAMIC,
    PAPER,
    RUBBER,
    OTHERS
  }
}
