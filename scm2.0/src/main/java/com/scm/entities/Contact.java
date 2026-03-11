package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Contact {
  @Id
  private String id;
  private String name;
  private String email;
  private String phoneNumber;
  private String address;
  private String picture;
  @Column(columnDefinition = "TEXT")
  private String descriptions;
  // private boolean favorite = false;
  private String websiteLink;
  private String GithubLink;
  // private List<SocialLinks> socialLinks = new ArrayList<>();
  private String cloudinaryImagePublicId;

  @ManyToOne
  @JsonIgnore
  private User user;

  @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
  private List<SocialLinks> socialLinks = new ArrayList<>();

}
