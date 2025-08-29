package com.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.scm.entities.Providers;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Entity(name = "user")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

  @Id
  private String userId;

  @Column(name = "user_name", nullable = false)
  private String name;
  @Column(unique = true, nullable = false)
  private String email;
  @Getter(AccessLevel.NONE)
  private String password;

  @Column(columnDefinition = "TEXT")
  private String about;

  @Column(columnDefinition = "TEXT")
  private String profilePic;

  private String phoneNumber;
  private boolean enabled = true;
  private boolean emailVerified = false;
  private boolean phoneVerified = false;
  // Self,google,twitter,linkedin,github
  @Enumerated(value = EnumType.STRING)
  private Providers provider = Providers.SELF;
  private String providerUserId;

  // add more table if needed

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  // one user has many contacts
  // CascadeType.ALL: all operations (PERSIST, MERGE, REMOVE) will be cascaded to
  // the contacts(means user delete update hoga then uske contacts bhi honge)
  private List<Contact> contacts = new ArrayList<>();

  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> roleList = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // List of roles [USER,ADMIN]
    // collection of simple granted authorities[roles]

    Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role))
        .collect(Collectors.toList());
    return roles;

  }

  // for this project email id and username is same
  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  @Override
  public String getPassword() {

    return this.password;

  }
}
