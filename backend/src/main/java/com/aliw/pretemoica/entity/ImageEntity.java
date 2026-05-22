package com.aliw.pretemoica.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "image")
public class ImageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String url;

  @Column(nullable = false)
  private String fileName;

  private String contentType;

  private Long fileSize;

  public ImageEntity() {}

  public ImageEntity(String url, String fileName) {
    this.url = url;
    this.fileName = fileName;
  }

  public ImageEntity(String url, String fileName, String contentType, Long fileSize) {
    this.url = url;
    this.fileName = fileName;
    this.contentType = contentType;
    this.fileSize = fileSize;
  }
}
