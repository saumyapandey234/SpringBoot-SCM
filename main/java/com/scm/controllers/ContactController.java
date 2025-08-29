package com.scm.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helper.AppConstants;
import com.scm.helper.Helper;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

  @Autowired
  private ImageService imageService;

  private Logger logger = LoggerFactory.getLogger(ContactController.class);

  @Autowired
  private ContactService contactService;

  @Autowired
  private UserService userService;

  // add contact page handler
  @RequestMapping("/add")
  public String addContactView(Model model) {
    ContactForm contactForm = new ContactForm();
    // contactForm.setName("Saumya Pandey");
    model.addAttribute("contactForm", new ContactForm());
    return "user/add_contact";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult r,
      Authentication authentication, HttpSession session) {

    // process the form data

    // validate form
    if (r.hasErrors()) {
      // to print the error on console
      r.getAllErrors().forEach(error -> {
        logger.info(error.toString());
      });
      session.setAttribute("message", Message.builder()
          .content("Please correct the following errors")
          .type(MessageType.red)
          .build());
      return "user/add_contact";
    }

    String username = Helper.getEmailOfLoggedInUser(authentication);
    // converting form to contact

    User user = userService.getUserByEmail(username);
    // process the contact picture

    // upload krne ka code
    String filename = UUID.randomUUID().toString();
    String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);

    // logger.info("file information:{}",
    // contactForm.getContactImage().getOriginalFilename());

    Contact contact = new Contact();
    contact.setName(contactForm.getName());
    contact.setEmail(contactForm.getEmail());
    contact.setPhoneNumber(contactForm.getPhoneNumber());
    contact.setAddress(contactForm.getAddress());
    contact.setDescriptions(contactForm.getDescriptions());
    contact.setUser(user);
    contact.setGithubLink(contactForm.getGithubLink());
    contact.setWebsiteLink(contactForm.getWebsiteLink());
    contact.setPicture(fileURL);
    contact.setCloudinaryImagePublicId(filename);
    contactService.save(contact);
    System.out.println(contactForm);

    // set the contact picture url

    // set the msg for diplay only.

    session.setAttribute("message", Message.builder()
        .content("New contact added successfully")
        .type(MessageType.green)
        .build());
    return "redirect:/user/contacts/add";
  }

  // view contact

  @RequestMapping("/view")
  public String viewContact(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size,
      @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
      @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
      Authentication authentication, Model model) {

    // load all the users contacts
    String username = Helper.getEmailOfLoggedInUser(authentication);

    User user = userService.getUserByEmail(username);

    Page<Contact> pageContact = contactService.getContactsByUser(user, page, size, sortBy, sortDirection);
    model.addAttribute("pageContact", pageContact);
    model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
    return "user/view_contact";
  }

}
