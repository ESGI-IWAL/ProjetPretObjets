package com.aliw.pretemoica.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "address")
public class AddressEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String street;
  @Column(nullable = false)
  private String city;
  @Column(nullable = false)
  private String postalCode;
  @Column(nullable = false)
  private String country;
  private String complement;
  private Double latitude;
  private Double longitude;
  public AddressEntity() {}
  public AddressEntity(String street, String city, String postalCode, String country) {
    this.street = street;
    this.city = city;
    this.postalCode = postalCode;
    this.country = country;
  }
}