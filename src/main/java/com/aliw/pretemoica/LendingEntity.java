package com.aliw.pretemoica;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class LendingEntity {
    @Id
    @GeneratedValue
    private Long id;


    // borrowedBy et offeredBy devraient plutot etre des liens vers la table Users
    private String borrowedBy;
    private String offeredBy;
    private Date startedAt;
    private Date endedAt;
    @OneToOne
    @JoinColumn
    private ObjectEntity object;

    public ObjectEntity getObject() {
        return object;
    }

    public void setObject(ObjectEntity object) {
        this.object = object;
    }

    public Date getStartedAt() {
        return startedAt;
    }
    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }
    public Date getEndedAt() {
        return endedAt;
    }
    public void setEndedAt(Date endedAt) {
        this.endedAt = endedAt;
    }
}
