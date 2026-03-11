package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;

@Controller
public class PageController {
  @Value("${emailjs.public-key}")
  private String emailjsPublicKey;

  @Value("${emailjs.service-id}")
  private String emailjsServiceId;

  @Value("${emailjs.template-id}")
  private String emailjsTemplateId;

  @Autowired
  private UserService userService;

  @GetMapping("/")
  public String index() {
    return "redirect:/home";

  }

  @RequestMapping("/home")
  public String home(Model model) {
    System.out.println("Home page handler");
    model.addAttribute("name", "Substring Techno");
    model.addAttribute("yt", "Taushief");
    model.addAttribute("githubrepo", "Github");

    return "home";
  }

  // about
  @RequestMapping("/about")
  public String about() {
    System.out.println("Page loading");
    return "about";
  }

  // service
  @RequestMapping("/service")
  public String service() {
    System.out.println("Services loading");
    return "service";
  }

  // contact page
  @GetMapping("/contact")
  public String contact(Model model) {
    System.out.println("Contact loading");
    model.addAttribute("emailjsPublicKey", emailjsPublicKey);
    model.addAttribute("emailjsServiceId", emailjsServiceId);
    model.addAttribute("emailjsTemplateId", emailjsTemplateId);
    return "contact";
  }

  // process login
  @GetMapping("/login")
  public String login() {
    return "login";
  }

  // registration page
  @GetMapping("/register")
  public String register(Model model) {
    UserForm userform = new UserForm();
    // add userform object to model
    // userform.setEmail("example@example.com");
    // userform.setName("Christopher Briney(Conrad)");
    // userform.setPassword("password");
    // userform.setPhoneNumber("1234567890");
    // userform.setAbout("The Summer l turned pretty");
    model.addAttribute("userform", userform);
    return "register";
  }

  // processing registration
  @RequestMapping(value = "/do-register", method = RequestMethod.POST)
  public String processRegister(@Valid @ModelAttribute("userform") UserForm userform, BindingResult result, Model model,
      HttpSession session) {
    System.out.println("Processing registration");
    // fetch form data
    // Userform
    System.out.println(userform);
    // validate form data
    if (result.hasErrors()) {
      model.addAttribute("userform", userform);
      return "register";
    }

    // save to database
    // userService

    // Userform to user
    // User user = User.builder()
    // .name(userform.getName())
    // .email(userform.getEmail())
    // .password(userform.getPassword())
    // .phoneNumber(userform.getPhoneNumber())
    // .about(userform.getAbout())
    // .profilePic("https://medium.com/@iramh18/chris-bait-how-briney-hooks-viewers-on-conrad-fisher-223c0515329b")
    // .build();

    User user = new User();
    user.setName(userform.getName());
    user.setEmail(userform.getEmail());
    user.setPassword(userform.getPassword());
    user.setPhoneNumber(userform.getPhoneNumber());
    user.setAbout(userform.getAbout());
    user.setEnabled(true);
    user.setProfilePic("https://medium.com/@iramh18/chris-bait-how-briney-hooks-viewers-on-conrad-fisher-223c0515329b");

    User savedUser = userService.saveUser(user);
    System.out.println("user saved");
    // message="Registration successful"
    // add the message
    Message message = Message.builder().content("Registration successful").type(MessageType.blue).build();
    session.setAttribute("message", message);

    // Redirect login page
    return "redirect:/register";

  }

}
