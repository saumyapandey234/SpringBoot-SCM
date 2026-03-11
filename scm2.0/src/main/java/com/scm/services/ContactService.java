package com.scm.services;

import com.scm.entities.Contact;
import com.scm.entities.User;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactService {
  // save contacts
  Contact save(Contact contact);

  Contact update(Contact contact);

  List<Contact> getContacts();

  Contact getContactById(String id);

  Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);

  Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);

  Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order,
      User user);

  List<Contact> getContactsByUserId(String userId);

  void deleteContact(String id);

  Page<Contact> getContactsByUser(User user, int page, int size, String sortField, String sortDirection);

  long countByUser(User user);

  // long countByUserAndFavourite(User user, boolean favourite);

}
