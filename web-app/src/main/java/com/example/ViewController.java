package com.example;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

  @RequestMapping("/")
  public String index() {
    return "index";
  }

  @RequestMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String admin() {
    return "admin";
  }

}
