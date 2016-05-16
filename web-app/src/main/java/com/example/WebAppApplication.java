package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.Map;

@SpringBootApplication
@EnableZuulProxy
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppApplication  {

  public static void main(String[] args) {
    SpringApplication.run(WebAppApplication.class, args);
  }

//  @Bean
//  public Servlet proxyServlet() {
//    return new ProxyServlet();
//  }
//
//  @Bean
//  public ServletRegistrationBean servletRegistrationBean(){
//    String mapping = "/uaa/*";
//    String target = "http://localhost:8235/uaa";
//    ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(proxyServlet(), mapping);
//    servletRegistrationBean.addInitParameter("targetUri", target);
//    servletRegistrationBean.addInitParameter(ProxyServlet.P_LOG, "true");
//    return servletRegistrationBean;
//  }

  @Bean
  public ProxyController proxyController() {
    return new ProxyController("http://localhost:8235/uaa");
  }

  @Bean
  public HandlerMapping handlerMapping() {
    SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
    ((Map<String,Object>)mapping.getUrlMap()).put("/uaa/**", proxyController());
    mapping.setOrder(1);
    return mapping;
  }
}
