package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.services.*;
import com.scm.helper.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;

@Service
public class ContactServiceImpl implements ContactService {

  @Autowired
  private ContactRepo contactRepo;

  @Override
  public Contact save(Contact contact) {
    String contactId = UUID.randomUUID().toString();
    contact.setId(contactId);
    return contactRepo.save(contact);
  }

  @Override
  public List<Contact> getContacts() {
    return contactRepo.findAll();

  }

  @Override
  public Contact getContactById(String id) {
    return contactRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id" + id));
  }

  @Override
  public Contact update(Contact contact) {
    return contactRepo.save(contact);
  }

  @Override
  public void deleteContact(String id) {
    Contact contact = contactRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id" + id));
    contactRepo.delete(contact);
  }

  @Override
  public List<Contact> searchContacts(String name, String email, String phoneNumber) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public List<Contact> getContactsByUserId(String userId) {
    return contactRepo.findByUserId(userId);
  }

  @Override
  public Page<Contact> getContactsByUser(User user, int page, int size, String sortBy, String direction) {
    Sort sort = sortBy.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    var pageable = PageRequest.of(page, size, sort);
    return contactRepo.findByUser(user, pageable);
  }
}
