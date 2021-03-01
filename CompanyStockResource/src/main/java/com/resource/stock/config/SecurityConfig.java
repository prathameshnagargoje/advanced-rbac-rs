package com.resource.stock.config;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
    jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakGrantedAuthoritiesConverter());
    return jwtConverter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .authorizeRequests()
        .mvcMatchers("/user/**")
        .hasAuthority("SCOPE_READ")
        .antMatchers(HttpMethod.GET, "/broker")
        .hasAuthority("SCOPE_CREATE")
        .antMatchers(HttpMethod.DELETE, "/broker")
        .hasAuthority("SCOPE_DELETE")
        .antMatchers(HttpMethod.POST, "/broker")
        .hasAuthority("SCOPE_CREATE")
        .anyRequest()
        .permitAll()
        .and()
        .oauth2ResourceServer()
        .jwt()
        .jwtAuthenticationConverter(jwtAuthenticationConverter());
  }
}

@Configuration
class KeycloakGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  private String resource_server = "CompanyStockResource";

  @SuppressWarnings("unchecked")
  @Override
  public Collection<GrantedAuthority> convert(Jwt source) {
    if (source.containsClaim("authorization")) {
      Map<String, Object> realmAccess = source.getClaimAsMap("authorization");
      List<Object> permissions = (List<Object>) realmAccess.get("permissions");
      ObjectMapper mapper = new ObjectMapper();
      List<String> scopes = null;
      for (Object permission : permissions) {
        try {
          Map<String, Object> jsonPermission = mapper.readValue(permission.toString(), Map.class);
          if (jsonPermission.get("rsname").toString().equalsIgnoreCase(resource_server)) {
            scopes = (List<String>) jsonPermission.get("scopes");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      return scopes
          .stream()
          .map(rn -> new SimpleGrantedAuthority("SCOPE_" + rn))
          .collect(Collectors.toList());
    }
    return null;
  }
}
