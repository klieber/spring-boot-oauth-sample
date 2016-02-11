package com.example;

import lombok.Getter;
import org.springframework.hateoas.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Entity
@Table(name = "USER")
public class User implements Serializable, Identifiable<Integer> {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "USERNAME")
  private String username;

  @Column(name = "FIRSTNAME")
  private String firstName;

  @Column(name = "LASTNAME")
  private String lastName;
}
