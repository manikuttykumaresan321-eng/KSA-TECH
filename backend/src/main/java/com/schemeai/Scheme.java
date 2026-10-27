package com.schemeai;
import jakarta.persistence.*;
@Entity @Table(name="schemes")
public class Scheme {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(nullable=false) public String name;
 @Column(nullable=false) public String category;
 @Column(length=1600) public String description;
 public int minAge=0;
 public int maxAge=120;
 public double maxAnnualIncome=10000000;
 public String occupation="any";
 public String state="All";
 public String benefit="Check official portal";
 public String officialUrl;
 public boolean active=true;
 public Scheme() {}
}