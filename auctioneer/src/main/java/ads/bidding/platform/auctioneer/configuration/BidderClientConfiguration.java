/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BidderClientConfiguration {

  private final Logger logger = LoggerFactory.getLogger(BidderClientConfiguration.class);

  /**
   * ToDo
   *
   * @return
   */
  @Bean()
  public RestTemplate bidderRestTemplate() {
    return new RestTemplate();
  }

}

