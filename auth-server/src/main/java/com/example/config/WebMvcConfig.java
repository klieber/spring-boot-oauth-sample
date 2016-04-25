package com.example.config;

import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.security.Principal;

@Controller
@EnableResourceServer
@SessionAttributes("authorizationRequest")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  @RequestMapping("/user")
  @ResponseBody
  public Principal user(Principal user) {
    return user;
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("login");
    registry.addViewController("/oauth/confirm_access").setViewName("authorize");
  }
}
