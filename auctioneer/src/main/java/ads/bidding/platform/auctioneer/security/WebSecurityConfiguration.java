/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.security;

import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletResponse;
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
 * ToDo
 */
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfiguration {

  private final Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);

  @Value("${app.user}")
  private String userName;

  @Value("${app.password}")
  private String password;

  @Bean
  public SecurityFilterChain authFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.exceptionHandling()
        .authenticationEntryPoint((request, response, e) -> {
          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          response.setContentType(MediaType.APPLICATION_JSON_VALUE);
          response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));
          response.getWriter().write("{\"message\": \"Sorry bud, u cannot enter the park!\"}");
        });
    httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    httpSecurity.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
    return httpSecurity.build();

  }

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user = User
        .withUsername(userName).password("{noop}" + password).roles("admin")
        .build();
    return new InMemoryUserDetailsManager(user);
  }

}
