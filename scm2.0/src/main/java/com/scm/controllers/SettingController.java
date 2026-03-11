package com.scm.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.forms.ContactSearchForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.repositories.UserRepo;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/contact")
@Controller
public class SettingController {

  @Autowired
  private BCryptPasswordEncoder b;

  @Autowired
  private UserRepo userRepo;

  @GetMapping("/settings")
  public String openSettings() {

    return "user/settings";
  }

  @PostMapping("/change-password")
  public String changePassword(@RequestParam("oldPassword") String oldPassword,
      @RequestParam("newPassword") String newPassword, Authentication authentication, HttpSession session) {

    System.out.println(oldPassword);
    System.out.println(newPassword);

    String username = authentication.getName();

    User currentUser = this.userRepo.findByEmail(username)
        .orElseThrow(() -> new RuntimeException("User not found"));

    System.out.println(currentUser.getPassword());

    if (this.b.matches(oldPassword, currentUser.getPassword())) {
      // change the password.
      currentUser.setPassword(this.b.encode(newPassword));
      this.userRepo.save(currentUser);
      session.setAttribute("message", new Message("Password changed", MessageType.green));

    } else {
      // error.
      session.setAttribute("message", new Message("Old password is incorrect", MessageType.red));

    }

    return "user/dashboard";

  }

}
