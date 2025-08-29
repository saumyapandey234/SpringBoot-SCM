package com.scm.config;

import org.springframework.security.config.Customizer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.scm.services.impl.SecurityCustomUserDetailsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

// @Configuration
// public class SecurityConfig {

// // user create and login using java code with in memory service
// // !st user
// @Bean
// public UserDetailsService u() {
// UserDetails user1 = User
// .withDefaultPasswordEncoder()
// .username("saumya")
// .password("saumya123")
// .roles("admin")
// .build();

// // 2nd user
// UserDetails user2 = User
// .withDefaultPasswordEncoder()
// .username("conrad")
// .password("conrad123")
// .build();

// var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,
// user2);

// return inMemoryUserDetailsManager;
// }

// }

// Database vale user ko login krna hai

@Configuration
public class SecurityConfig {

  @Autowired
  private OAuthenticationSuccessHandler handler;

  @Autowired
  private SecurityCustomUserDetailsService securityCustomUserDetailsService;

  // configuration of authentication provider for spring security
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    // user detail service ka object:
    daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailsService);
    // password encoder ka object
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

    return daoAuthenticationProvider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    // configurations
    // urls configure kiya hai ki konse url public rahenge and konse private
    httpSecurity.authorizeHttpRequests(authorize -> {
      // authorize.requestMatchers("/home", "/register", "/service").permitAll();
      authorize.requestMatchers("/user/**").authenticated();
      authorize.anyRequest().permitAll();
    });

    // form default login
    // Force redirect to dashboard after login

    // agr hume kuch change krna hua toh hum yaha aaenge form login se related.
    httpSecurity.formLogin(formLogin -> {
      formLogin.loginPage("/login"); // your login.html
      formLogin.loginProcessingUrl("/authenticate"); // form action
      formLogin.defaultSuccessUrl("/user/profile", true); // redirect always after login on local db login.
      formLogin.failureUrl("/login?error=true"); // redirect back with error flag
      formLogin.usernameParameter("email"); // match your form input name
      formLogin.passwordParameter("password");

    });

    // formLogin.failureHandler(new AuthenticationFailureHandler() {

    // @Override
    // public void onAuthenticationFailure(HttpServletRequest request,
    // HttpServletResponse response,
    // AuthenticationException exception) throws IOException, ServletException {

    // throw new UnsupportedOperationException("Unimplemented method
    // 'onAuthenticationFailure'");
    // }

    // });

    // formLogin.successHandler(new AuthenticationSuccessHandler() {

    // @Override
    // public void onAuthenticationSuccess(HttpServletRequest request,
    // HttpServletResponse response,
    // Authentication authentication) throws IOException, ServletException {

    // throw new UnsupportedOperationException("Unimplemented method
    // 'onAuthenticationSuccess'");
    // }

    // });

    httpSecurity.csrf(AbstractHttpConfigurer::disable);

    httpSecurity.logout(logout -> {
      logout.logoutUrl("/do-logout");
      logout.logoutSuccessUrl("/login?logout=true");
    });

    // oauth configurations
    httpSecurity.oauth2Login(oauth -> {
      oauth.loginPage("/login");
      oauth.successHandler(handler);
    });

    return httpSecurity.build();

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
