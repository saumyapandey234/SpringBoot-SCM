package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.*;
import org.slf4j.Logger;
// ...existing code...
import org.slf4j.LoggerFactory;

import com.scm.entities.User;
import com.scm.helper.AppConstants;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repositories.UserRepo;
import com.scm.services.UserService;

@Service
// user service implementation class which implements all the methods of user
// service class.
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepo userRepo;// to help save the data or interact with the database
  @Autowired
  private PasswordEncoder passwordEncoder;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public User saveUser(User user) {
    // user id : have to generate
    String userId = UUID.randomUUID().toString();
    user.setUserId(userId);
    // password encode
    // user.setPassword(userId);
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // set the user role
    user.setRoleList(List.of(AppConstants.ROLE_USER)); // or set to default role if needed

    logger.info("Provider: {}", user.getProvider() != null ? user.getProvider().toString() : "NONE");

    return userRepo.save(user);
  }

  @Override
  public Optional<User> getUserById(String id) {
    return userRepo.findById(id);
  }

  @Override
  public Optional<User> updateUser(User user) {
    User existingUser = userRepo.findById(user.getUserId())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    existingUser.setName(user.getName());
    existingUser.setEmail(user.getEmail());
    existingUser.setAbout(user.getAbout());
    existingUser.setPhoneNumber(user.getPhoneNumber());
    existingUser.setProfilePic(user.getProfilePic());
    existingUser.setEnabled(user.isEnabled());
    existingUser.setEmailVerified(user.isEmailVerified());
    existingUser.setPhoneVerified(user.isPhoneVerified());
    existingUser.setProvider(user.getProvider());
    existingUser.setProviderUserId(user.getProviderUserId());

    // Password encode only if provided
    if (user.getPassword() != null && !user.getPassword().isBlank()) {
      existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    return Optional.of(userRepo.save(existingUser));
  }

  @Override
  public void deleteUser(String id) {
    User user2 = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    userRepo.delete(user2);
  }

  @Override
  public boolean isUserExists(String userId) {
    User user2 = userRepo.findById(userId).orElse(null);
    return user2 != null ? true : false;
  }

  @Override
  public boolean isUserExistsByEmail(String email) {
    User user = userRepo.findByEmail(email).orElse(null);
    return user != null ? true : false;
  }

  @Override
  public boolean isUserExistsByUserName(String userName) {

    User user = userRepo.findByName(userName).orElse(null);
    return user != null ? true : false;
  }

  @Override
  public List<User> getAllUsers() {
    return userRepo.findAll();

  }

  @Override
  public User getUserByEmail(String email) {

    return userRepo.findByEmail(email).orElse(null);

  }

}
