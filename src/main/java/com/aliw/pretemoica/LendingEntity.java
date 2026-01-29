package com.aliw.pretemoica;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

public class LendingEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String borrowedBy;
    private String offeredBy;
    private Date startedAt;
    private Date endedAt;
    private ObjectEntity object;
}
