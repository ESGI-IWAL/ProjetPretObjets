package com.aliw.pretemoica.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ObjectEntity> objects = new ArrayList<>();

    @ManyToMany
    private List<UserEntity> linkedUsers = new ArrayList<>();

    private Integer rating = 0;

    public UserEntity() {
        // Required by JPA and JSON deserialization.
    }

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


    public Long getId() { return id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<ObjectEntity> getObjects() { return objects; }

    public List<UserEntity> getLinkedUsers() { return linkedUsers; }

    public void setRating(Integer rating) { this.rating = rating; }
}