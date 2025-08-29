package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Providers;
import com.scm.entities.User;
import com.scm.helper.AppConstants;
import com.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
// we make component so that we can directly autowire it.
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  Logger logger = LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);
  @Autowired
  private UserRepo userRepo;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    logger.info("OAuthAuthenticationSuccessHandler");

    // idenity the provider

    var OAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
    String authorizedClientRegistrationId = OAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
    logger.info(authorizedClientRegistrationId);

    var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
    oauthUser.getAttributes().forEach((key, value) -> {
      logger.info(key + " : " + value);
    });

    User user = new User();
    user.setUserId((UUID.randomUUID().toString()));
    user.setRoleList(List.of(AppConstants.ROLE_USER));
    user.setEmailVerified(true);
    user.setEnabled(true);
    // user.setPassword("dummy");

    if (authorizedClientRegistrationId.equals("google")) {
      // google login
      // google attributes
      user.setEmail(oauthUser.getAttribute("email").toString());
      user.setProfilePic(oauthUser.getAttribute("picture").toString());
      user.setName(oauthUser.getAttribute("name").toString());
      user.setProviderUserId(oauthUser.getName());
      user.setProvider(Providers.GOOGLE);
      user.setAbout("This is google login");

    } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
      // github login
      // github attributes
      String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
          : oauthUser.getAttribute("login").toString() + "@gmail.com";
      // ...existing code...
      String picture = oauthUser.getAttribute("avatar_url").toString();

      String name = oauthUser.getAttribute("login").toString();
      String providerUserId = oauthUser.getName();

      user.setEmail(email);
      user.setProfilePic(picture);
      user.setName(name);
      user.setProviderUserId(providerUserId);
      user.setProvider(Providers.GITHUB);
      user.setAbout("This is github login");

    } else {
      // other providers
      logger.info("OAuthAuthenticationSuccessHandler : Unknown provider");
    }

    // save the user

    User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
    if (user2 == null) {
      userRepo.save(user);
    }
    new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");// on social login always redirect to
                                                                                   // user/profile

    // DefaultOAuth2User oauthUser = (DefaultOAuth2User)
    // authentication.getPrincipal();
    // // logger.info(oauthUser.getName());

    // // oauthUser.getAttributes().forEach((key, value) -> {
    // // logger.info("Key: {}, Value: {}", key, value);
    // // });

    // // logger.info(oauthUser.getAuthorities().toString());

    // // saving data to database

    // String email = oauthUser.getAttribute("email").toString();
    // String name = oauthUser.getAttribute("name").toString();
    // String profilepic = oauthUser.getAttribute("picture").toString();

    // // create user object and save to database
    // User user1 = new User();
    // user1.setEmail(email);
    // user1.setName(name);
    // user1.setProfilePic(profilepic);
    // user1.setPassword("password");
    // user1.setUserId(UUID.randomUUID().toString());
    // user1.setProvider(Providers.GOOGLE);
    // user1.setEnabled(true);
    // user1.setEmailVerified(true);
    // user1.setProviderUserId(oauthUser.getName());
    // user1.setAbout("This account is created using google");

    // User user2 = userRepo.findByEmail(email).orElse(null);
    // if (user2 == null) {
    // userRepo.save(user1);
    // logger.info("User created with email: " + email);
    // }

    // response.sendRedirect("/home");

  }

}
