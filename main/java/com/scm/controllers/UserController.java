package com.scm.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.helper.Helper;
import com.scm.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

  private Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  // user dashboard
  @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
  public String userDashboard() {
    System.out.println("User dashboard loading");
    return "user/dashboard";

  }

  @RequestMapping(value = "/profile", method = RequestMethod.GET)
  public String userProfile(Model model, Authentication authentication) {
    // String username = Helper.getEmailOfLoggedInUser(authentication);
    // logger.info("User logged in : {}", username);
    // // we can fetch data from database.
    // User user = userService.getUserByEmail(username);
    // System.out.println(user.getName());
    // System.out.println(user.getEmail());
    // model.addAttribute("loggedInUser", user);

    // logger.info("User profile loading");
    return "user/profile";
  }

  // user add contact page
  // @RequestMapping(value="/add-contact", method = RequestMethod.GET)

  // user view contacts

  // user edit contact page

  // user delete contact page

}
