/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.utils;

import java.util.Arrays;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@UtilityClass
@PropertySource("classpath:application.yml")
public class Commons {

  private final Logger logger = LoggerFactory.getLogger(Commons.class);

  @Value("${bidders}")
  public String bidders;

  /**
   * ToDo
   *
   * @return
   * @throws Exception
   */
  @Bean
  public List<String> getAvailableBidders() throws Exception {
    if (bidders != null && bidders.isBlank()) {
      throw new Exception("Configuration for bidders service is not available!");
    }
    final List<String> bidderClients = Arrays.asList(bidders.split(","));
    logger.debug("bidderClients={}", bidderClients.toString());
    return bidderClients;
  }
}