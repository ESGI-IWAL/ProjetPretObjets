package com.aliw.pretemoica;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class LendingHistoryEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String borrowedBy;
    private String offeredBy;
    private Date startedAt;
    private Date endedAt;


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
