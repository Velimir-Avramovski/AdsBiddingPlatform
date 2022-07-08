/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Handles basic authentication of this service.
 */
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfiguration {

  private final Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);

  @Value("${app.user}")
  private String userName;

  @Value("${app.password}")
  private String password;

  private final ObjectMapper objectMapper;

  /**
   * AuthenticationError error object that we gonna return in case of an error.
   */
  @Data
  @AllArgsConstructor
  private class AuthenticationError {

    private int httpStatus;
    private String message;

    @Override
    public String toString() {
      String authenticationErrorAsString;
      try {
        authenticationErrorAsString = objectMapper.writeValueAsString(new AuthenticationError(httpStatus, message));
      } catch (JsonProcessingException e) {
        return httpStatus + " - " + message;
      }
      return authenticationErrorAsString;
    }

  }

  /**
   * Setup a basic auth for this service. Introduce stateless session and also handle out custom exception when the user is not
   * authenticated.
   *
   * @param httpSecurity httpSecurity.
   * @return SecurityFilterChain securityFilterChain.
   * @throws Exception throw when something goes wrong with the authentication setup.
   */
  @Bean
  public SecurityFilterChain authFilterChain(HttpSecurity httpSecurity) throws Exception {
    // Setup of the basic authentication.
    httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    httpSecurity.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();

    // Basic exception handling.
    httpSecurity.exceptionHandling().authenticationEntryPoint((request, response, e) -> {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
      final AuthenticationError authenticationError =
          new AuthenticationError(HttpServletResponse.SC_UNAUTHORIZED, "Sorry bud, u cannot enter the park!");
      response.getWriter().write(authenticationError.toString());
    });

    return httpSecurity.build();
  }

  /**
   * Sets credential for the authentication of the user.
   *
   * @return InMemoryUserDetailsManager.
   */
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user = User
        .withUsername(userName).password("{noop}" + password).roles("admin")
        .build();
    return new InMemoryUserDetailsManager(user);
  }

}
