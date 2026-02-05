package com.aliw.pretemoica;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Contrainte unique sur l'email
    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    // Relation OneToMany : Un utilisateur possède plusieurs objets
    // "mappedBy" doit correspondre au nom du champ UserEntity dans ObjectEntity
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ObjectEntity> objects = new ArrayList<>(); // Initialisation

    // Relation ManyToMany pour les utilisateurs liés (amis/réseau)
    @ManyToMany
    private List<UserEntity> linkedUsers = new ArrayList<>(); // Initialisation

    private Integer rating = 0; // Valeur par défaut pour éviter le null

    public UserEntity() {
        //En attente
    }

    // --- LOGIQUE MÉTIER ---

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

    /**
     * Gestion du rating avec erreur si null
     */
    public Integer getRating() {
        if (this.rating == null) {
            throw new IllegalStateException("Le rating n'a pas été initialisé pour l'utilisateur : " + this.email);
        }
        return rating;
    }

    public boolean authentification(String email, String password) {
        if (this.email == null || this.password == null) return false;
        return this.email.equals(email) && this.password.equals(password);
    }

    // --- GETTERS & SETTERS ---

    public Long getId() { return id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<ObjectEntity> getObjects() { return objects; }
    public void setObjects(List<ObjectEntity> objects) { this.objects = objects; }

    public List<UserEntity> getLinkedUsers() { return linkedUsers; }
    public void setLinkedUsers(List<UserEntity> linkedUsers) { this.linkedUsers = linkedUsers; }

    public void setRating(Integer rating) { this.rating = rating; }
}