package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableZuulProxy
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAppApplication  {
//
//  @Autowired
//  private OAuth2ClientContextFilter oauth2ClientContextFilter;

//  @Autowired
//  private OAuth2AuthenticationProcessingFilter oauth2AuthenticationProcessingFilter;
//  
    
  public static void main(String[] args) {
    SpringApplication.run(WebAppApplication.class, args);
  }
  
//  @Bean
//  public OAuth2AuthenticationProcessingFilter oauth2AuthenticationProcessingFilter() {
//    return new OAuth2AuthenticationProcessingFilter();
//  }
//  
//  @Override
//  public void configure(HttpSecurity http) throws Exception {
//    http.logout().and().antMatcher("/**").authorizeRequests()
//        .antMatchers("/login").permitAll()
//        .anyRequest().authenticated().and().csrf()
//        .csrfTokenRepository(csrfTokenRepository()).and()
//        .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
//        .addFilterAfter(oauth2ClientContextFilter, SecurityContextPersistenceFilter.class);
//  }
//
//  private Filter csrfHeaderFilter() {
//    return new OncePerRequestFilter() {
//      @Override
//      protected void doFilterInternal(HttpServletRequest request,
//                                      HttpServletResponse response, FilterChain filterChain)
//          throws ServletException, IOException {
//        CsrfToken csrf = (CsrfToken) request
//            .getAttribute(CsrfToken.class.getName());
//        if (csrf != null) {
//          Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
//          String token = csrf.getToken();
//          if (cookie == null
//              || token != null && !token.equals(cookie.getValue())) {
//            cookie = new Cookie("XSRF-TOKEN", token);
//            cookie.setPath("/");
//            response.addCookie(cookie);
//          }
//        }
//        filterChain.doFilter(request, response);
//      }
//    };
//  }
//  
//  private CsrfTokenRepository csrfTokenRepository() {
//    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//    repository.setHeaderName("X-XSRF-TOKEN");
//    return repository;
//  }
  
//  @Autowired
//  private OAuth2RestOperations restTemplate;
//
//	@RequestMapping(value="/test", method = RequestMethod.GET)
//  public ResponseEntity<?> getTest() {
//    Object o = restTemplate.getForObject("http://localhost:9081/users/1", Object.class);
//    return ResponseEntity.ok(o);
//  }
//
//  @Bean
//  @ConfigurationProperties("spring.oauth2.client")
//  @Primary
//  public ClientCredentialsResourceDetails oauth2RemoteResource() {
//    ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
//    details.setClientId("webapp");
//    details.setClientSecret("secret");
//    details.setAccessTokenUri("http://localhost:9081/oauth/token");
//    details.setScope(Arrays.asList("read", "write"));
//    return details;
//  }
//
//  @Bean
//  @Primary
//  public OAuth2RestTemplate oauth2RestTemplate(
//      OAuth2ClientContext oauth2ClientContext,
//      OAuth2ProtectedResourceDetails details) {
//    OAuth2RestTemplate template = new OAuth2RestTemplate(details,
//        oauth2ClientContext);
//    return template;
//  }
//
//  @Bean
//  public ZuulFilter oauth2ZuulFilter() {
//    return new ZuulFilter() {
//      @Override
//      public String filterType() {
//        return "pre";
//      }
//
//      @Override
//      public int filterOrder() {
//        return 1;
//      }
//
//      @Override
//      public boolean shouldFilter() {
//        return true;
//      }
//
//      @Override
//      public Object run() {
//        RequestContext ctx = RequestContext.getCurrentContext();
//
//        OAuth2AccessToken accessToken = restTemplate.getAccessToken();
//
//        ctx.addZuulRequestHeader("Authorization", "Bearer "+accessToken.getValue());
//
//        return null;
//      }
//    };
//  }
}
