package com.scm.helper;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

  public static String getEmailOfLoggedInUser(Authentication authentication) {
    if (authentication instanceof OAuth2AuthenticationToken) {
      var oAuth2Token = (OAuth2AuthenticationToken) authentication;
      var clientId = oAuth2Token.getAuthorizedClientRegistrationId();
      var oauth2User = (OAuth2User) authentication.getPrincipal();
      String username = "";

      if (clientId.equalsIgnoreCase("google")) {
        username = oauth2User.getAttribute("email");
      } else if (clientId.equalsIgnoreCase("github")) {
        username = oauth2User.getAttribute("email") != null
            ? oauth2User.getAttribute("email")
            : oauth2User.getAttribute("login") + "@gmail.com";
      }
      return username; // ✅ return actual email
    } else {
      System.out.println("Getting data from local database");
      return authentication.getName();
    }
  }

}
