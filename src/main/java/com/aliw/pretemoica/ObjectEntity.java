
package com.aliw.pretemoica;

import jakarta.persistence.Basic;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class ObjectEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Basic
    private String name;
    @Basic
    private String ownedBy;
    @Basic
    private String status;
    @Basic
    private String state;

}
