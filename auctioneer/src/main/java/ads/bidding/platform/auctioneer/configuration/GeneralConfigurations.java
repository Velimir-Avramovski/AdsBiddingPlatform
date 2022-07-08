/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration that handles creating of general purpose used beans. Example: Creates RestTemplate bean used to send requests to the bidder
 * services.
 */
@Configuration
public class GeneralConfigurations {

  private final Logger logger = LoggerFactory.getLogger(GeneralConfigurations.class);

  /**
   * Creates bean of RestTemplate type.
   *
   * @return RestTemplate instance.
   */
  @Bean()
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  /**
   * Creates bean of Json Object mapping.
   *
   * @return ObjectMapper instance.
   */
  @Bean()
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

}

