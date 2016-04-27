package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class ViewController {

  @Autowired
  private RestTemplate restTemplate;

  @Value("${app.rest-api.host}")
  private String apiHost;

  @Value("${app.auth-server.host}")
  private String authServerHost;

  @RequestMapping("/logout")
  public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    String redirectTo = ServletUriComponentsBuilder.fromCurrentServletMapping().toUriString();
    return "redirect:"+authServerHost+"/logout?redirectTo="+redirectTo;
  }

  @RequestMapping("/")
  public String index() {
    return "index";
  }

  @RequestMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String admin(Principal principal, Model model) {

    ResponseEntity<Resource<User>> response = restTemplate.exchange(
      apiHost+"/users/search/findByUsername?username="+principal.getName(),
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<Resource<User>>(){}
    );
    Resource<User> userResource = response.getBody();
    model.addAttribute("user", userResource.getContent());
    return "admin";
  }

  public static class User {
    private String username;
    private String firstName;
    private String lastName;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }
  }
}
