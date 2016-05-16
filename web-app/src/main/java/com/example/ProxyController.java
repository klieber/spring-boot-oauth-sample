package com.example;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ServletWrappingController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class ProxyController extends ServletWrappingController {

  public ProxyController(String targetUri) {
    setServletClass(ProxyServlet.class);
    setServletName("proxyServlet");
    setSupportedMethods((String[]) null);
    Properties properties = new Properties();
    properties.setProperty("targetUri", targetUri);
    setInitParameters(properties);
  }

  @Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    return super.handleRequestInternal(new AuthorizedHttpServletRequest(request), response);
  }

  static class AuthorizedHttpServletRequest extends HttpServletRequestWrapper {

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public AuthorizedHttpServletRequest(HttpServletRequest request) {
      super(request);
    }

    /**
     * The default behavior of this method is to return getHeader(String name)
     * on the wrapped request object.
     */
    @Override
    public String getHeader(String name) {
      if (name.equalsIgnoreCase("Authorization")) {
        return getAuthorizationHeader();
      } else {
        return super.getHeader(name);
      }
    }

    /**
     * The default behavior of this method is to return getHeaders(String name)
     * on the wrapped request object.
     */
    @Override
    public Enumeration<String> getHeaders(String name) {
      if (name.equalsIgnoreCase("Authorization")) {
        return Collections.enumeration(Arrays.asList(getHeader(name)));
      } else {
        return super.getHeaders(name);
      }
    }

    /**
     * The default behavior of this method is to return getHeaderNames() on the
     * wrapped request object.
     */
    @Override
    public Enumeration<String> getHeaderNames() {
      List<String> names = new ArrayList<String>();
      names.addAll(Collections.list(super.getHeaderNames()));
      names.add("Authorization");
      return Collections.enumeration(names);
    }

    @Override
    public String getPathInfo()
    {
      String path = super.getPathInfo();
      if (path != null && path.startsWith("/uaa"))
      {
        final int length = "/uaa".length();
        path = path.substring(length);
      }
      return "/user";
    }

    @Override
    public String getServletPath()
    {
      return super.getServletPath();
    }

    private String getAuthorizationHeader() {
      String authHeader = "";
      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      if (auth instanceof OAuth2Authentication) {
        Object details = auth.getDetails();
        if (details instanceof OAuth2AuthenticationDetails) {
          OAuth2AuthenticationDetails oauth = (OAuth2AuthenticationDetails) details;
          String token = oauth.getTokenValue();
          String tokenType = oauth.getTokenType() == null ? "Bearer" : oauth.getTokenType();
          authHeader = tokenType + " " + token;
        }
      }
      return authHeader;
    }

  }
}
