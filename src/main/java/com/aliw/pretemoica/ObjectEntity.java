
package com.aliw.pretemoica;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ObjectEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Basic
    private String name;

    // ownedBy devrait aussi etre lie a un userId
    @Basic
    private String ownedBy;
    @Basic
    private String status;
    @Basic
    private String state;


    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
