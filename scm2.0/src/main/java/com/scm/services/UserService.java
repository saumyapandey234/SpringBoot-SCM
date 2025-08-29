package com.scm.services;

import java.util.List;
import java.util.Optional;

import com.scm.entities.User;

public interface UserService {
  User saveUser(User user);

  Optional<User> getUserById(String id);// optional tell us whether user is there or not

  Optional<User> updateUser(User user);

  void deleteUser(String id);

  boolean isUserExists(String userId);

  boolean isUserExistsByUserName(String userName);

  boolean isUserExistsByEmail(String email);

  List<User> getAllUsers();

  User getUserByEmail(String email);

  // add more methods as needed related user service[business logic] means it have
  // all the methods which we need to perfrom with user like updating user,adding
  // new user ,deleting user,getting user based on email,etc.

}
