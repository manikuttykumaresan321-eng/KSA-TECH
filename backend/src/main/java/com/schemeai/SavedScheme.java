package com.schemeai;
import jakarta.persistence.*;
@Entity @Table(name="saved_schemes", uniqueConstraints=@UniqueConstraint(columnNames={"user_id","scheme_id"}))
public class SavedScheme {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
 @Column(name="user_id",nullable=false) public Long userId;
 @Column(name="scheme_id",nullable=false) public Long schemeId;
 public SavedScheme() {}
 public SavedScheme(Long userId,Long schemeId){this.userId=userId;this.schemeId=schemeId;}
}