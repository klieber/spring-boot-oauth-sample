package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class ViewController {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ViewController.class);
  
  @RequestMapping("/")
  public String index(Principal principal) {
    LOGGER.info("Current user: " + principal.getName());
    return "index";
  }
  
  @RequestMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String admin() {
    return "admin";
  }
  
}
