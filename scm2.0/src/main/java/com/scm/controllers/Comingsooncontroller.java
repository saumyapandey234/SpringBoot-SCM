package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Comingsooncontroller {

  // All unbuilt/restricted routes go here → shows "Oops" page
  @RequestMapping("/user/update")
  public String editProfile() {
    return "user/coming_soon";
  }

  @RequestMapping("/contact/settings")
  public String settings() {
    return "user/coming_soon";
  }

  // Add more routes here as needed:
  // @RequestMapping("/user/contacts/delete")
  // public String deleteContact() { return "user/coming_soon"; }

}