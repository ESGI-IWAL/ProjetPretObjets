package com.aliw.pretemoica;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Basic
    private String email;

    @Basic
    private String password;

    private List<ObjectEntity> objects;

    private List<UserEntity> linkedUsers;

    private Integer rating;


    public Void addObject(ObjectEntity objectToAdd) {
        objects.add(objectToAdd);
        return null;
    }

    public int getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }


    public Void addLinkedUser(UserEntity userToAdd) {
        linkedUsers.add(userToAdd);
        return null;
    }
    public List<UserEntity> getLinkedUsers() {
        return linkedUsers;
    }

    public boolean authentification (String email , String password){

        return this.email.equals(email) && this.password.equals(password);
    }



}
