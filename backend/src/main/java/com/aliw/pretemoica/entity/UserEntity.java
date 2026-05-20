package com.aliw.pretemoica.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "app_user")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String username;

  @Column(unique = true, nullable = false)
  private String email;

  private String password;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ObjectEntity> objects = new ArrayList<>();

  @ManyToMany private List<UserEntity> linkedUsers = new ArrayList<>();

  private Integer rating = 0;

  public UserEntity() {}

  public void addObject(ObjectEntity objectToAdd) {
    if (this.objects == null) {
      this.objects = new ArrayList<>();
    }
    objects.add(objectToAdd);
  }

  public void addLinkedUser(UserEntity userToAdd) {
    if (this.linkedUsers == null) {
      this.linkedUsers = new ArrayList<>();
    }
    linkedUsers.add(userToAdd);
  }

  /** Gestion du rating avec erreur si null */
  public Integer getRating() {
    if (this.rating == null) {
      throw new IllegalStateException(
          "Le rating n'a pas été initialisé pour l'utilisateur : " + this.email);
    }
    return rating;
  }

  public boolean authentification(String email, String password) {
    if (this.email == null || this.password == null) return false;
    return this.email.equals(email) && this.password.equals(password);
  }
}
