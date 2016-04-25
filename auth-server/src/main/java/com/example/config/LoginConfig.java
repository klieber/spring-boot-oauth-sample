package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(-20)
public class LoginConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  @SuppressWarnings("SpringJavaAutowiringInspection")
  private AuthenticationManager authenticationManager;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
      .formLogin().loginPage("/login").permitAll()
      .and()
      .requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")
      .and()
      .authorizeRequests().anyRequest().authenticated();
    // @formatter:on
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.parentAuthenticationManager(authenticationManager);
  }
}
