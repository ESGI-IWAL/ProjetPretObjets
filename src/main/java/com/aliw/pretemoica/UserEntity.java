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
}
