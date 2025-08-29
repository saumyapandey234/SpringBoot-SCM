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

  List<Contact> searchContacts(String name, String email, String phoneNumber);

  List<Contact> getContactsByUserId(String userId);

  void deleteContact(String id);

  Page<Contact> getContactsByUser(User user, int page, int size, String sortField, String sortDirection);

}
