package com.example;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@SpringBootApplication
@EnableOAuth2Client
@EnableZuulProxy
@RestController
public class WebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppApplication.class, args);
	}

  @Autowired
  private OAuth2RestOperations restTemplate;

	@RequestMapping(value="/test", method = RequestMethod.GET)
  public ResponseEntity<?> getTest() {
    Object o = restTemplate.getForObject("http://localhost:9081/users/1", Object.class);
    return ResponseEntity.ok(o);
  }

  @Bean
  @ConfigurationProperties("spring.oauth2.client")
  @Primary
  public ClientCredentialsResourceDetails oauth2RemoteResource() {
    ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
    details.setClientId("webapp");
    details.setClientSecret("secret");
    details.setAccessTokenUri("http://localhost:9081/oauth/token");
    details.setScope(Arrays.asList("read", "write"));
    return details;
  }

  @Bean
  @Primary
  public OAuth2RestTemplate oauth2RestTemplate(
      OAuth2ClientContext oauth2ClientContext,
      OAuth2ProtectedResourceDetails details) {
    OAuth2RestTemplate template = new OAuth2RestTemplate(details,
        oauth2ClientContext);
    return template;
  }

  @Bean
  public ZuulFilter oauth2ZuulFilter() {
    return new ZuulFilter() {
      @Override
      public String filterType() {
        return "pre";
      }

      @Override
      public int filterOrder() {
        return 1;
      }

      @Override
      public boolean shouldFilter() {
        return true;
      }

      @Override
      public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        OAuth2AccessToken accessToken = restTemplate.getAccessToken();

        ctx.addZuulRequestHeader("Authorization", "Bearer "+accessToken.getValue());

        return null;
      }
    };
  }
}
