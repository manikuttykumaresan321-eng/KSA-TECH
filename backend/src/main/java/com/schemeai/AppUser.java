package com.schemeai;
import jakarta.persistence.*;
@Entity @Table(name="app_users")
public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(nullable=false) public String name;
 @Column(nullable=false, unique=true) public String email;
 @Column(nullable=false) public String passwordHash;
 @Column(nullable=false) public String role="USER";
 public AppUser() {}
 public AppUser(String name,String email,String passwordHash,String role){this.name=name;this.email=email;this.passwordHash=passwordHash;this.role=role;}
}