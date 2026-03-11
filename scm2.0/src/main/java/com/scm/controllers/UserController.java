package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.helper.Helper;
import com.scm.services.ContactService;
import com.scm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

  private Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private ContactService contactService;

  // user dashboard
  @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
  public String userDashboard(Model model, Authentication authentication) {
    String username = Helper.getEmailOfLoggedInUser(authentication);
    User user = userService.getUserByEmail(username);
    model.addAttribute("loggedInUser", user);
    return "user/dashboard";
  }

  // user profile
  @RequestMapping(value = "/profile", method = RequestMethod.GET)
  public String userProfile(Model model, Authentication authentication) {
    String username = Helper.getEmailOfLoggedInUser(authentication);
    User user = userService.getUserByEmail(username);

    // total contacts saved by this user
    long totalContacts = contactService.countByUser(user);

    // favourite contacts (contacts marked as favourite)
    // long favouriteContacts = contactService.countByUserAndFavourite(user, true);

    model.addAttribute("loggedInUser", user);
    model.addAttribute("totalContacts", totalContacts);
    // model.addAttribute("favouriteContacts", favouriteContacts);

    logger.info("Profile loaded for: {}, total contacts: {}", username, totalContacts);
    return "user/profile";
  }

}