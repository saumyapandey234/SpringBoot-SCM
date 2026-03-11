package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.repositories.UserRepo;

//import com.scm.services.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotController {

  // @Autowired
  // private EmailService emailService;

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  // email id form openEmailIdHandler

  @GetMapping("/forgot")
  public String openEmailIdHandler() {
    return "forgot_email_form";
  }

  @PostMapping("/send-otp")
  public String sendOtpHandler(@RequestParam("email") String email, HttpSession session) {
    System.out.println("Email: " + email);

    // generating otp of 4 digit.
    int otp = (int) (Math.random() * 9000) + 1000;
    System.out.println("OTP: " + otp);

    // // Here you would typically send the OTP to the user's email address.
    // boolean flag = this.emailService.sendEmail("OTP From SCM", "Your OTP is: " +
    // otp, email);
    // if (flag) {
    session.setAttribute("myotp", otp);
    session.setAttribute("email", email);
    // return "verify_otp_form";
    // } else {
    // session.setAttribute("message", "Invalid email address");
    // return "forgot_email_form";
    // }

    // // For demonstration purposes, we'll just print it to the console.
    return "verify_otp_form";
  }

  @PostMapping("/verify-otp")
  public String verifyOtpHandler(@RequestParam("otp") int otp, HttpSession session) {
    int myOtp = (int) session.getAttribute("myotp");
    String email = (String) session.getAttribute("email");

    if (myOtp == otp) {
      // Password change form
      // redirect to password change page
      User user = this.userRepo.getUserByEmail(email);
      if (user == null) {
        // send error message
        session.setAttribute("message", "User does not exist with this email");
        return "forgot_email_form";
      } else {
        // user present
        return "redirect:/change-password";
      }

    } else {
      session.setAttribute("message", "You have entered a wrong OTP");
      return "verify_otp_form";
    }
  }

  @GetMapping("/change-password")
  public String openPasswordChangeForm(@RequestParam("newpassword") String newpassword, HttpSession session) {

    String email = (String) session.getAttribute("email");
    User user = this.userRepo.getUserByEmail(email);
    user.setPassword(this.passwordEncoder.encode(newpassword));
    this.userRepo.save(user);
    session.setAttribute("message", "Your password is changed successfully.");

    return "redirect:/login?change=password change successfully..."; // this should match your Thymeleaf template
  }

}
