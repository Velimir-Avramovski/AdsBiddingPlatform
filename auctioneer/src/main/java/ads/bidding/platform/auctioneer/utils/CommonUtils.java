/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommonUtils {

  private final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

  @Autowired
  private final Environment environment;

  /**
   * ToDo
   *
   * @return
   * @throws Exception
   */
  public List<String> getAvailableBidders() {
    final String bidders = environment.getProperty("bidders");
    if (bidders != null && bidders.isBlank()) {
      logger.debug("Configuration for bidders service is not available!");
      return Collections.emptyList();
    } else {
      final List<String> bidderClients = Arrays.asList(bidders.split(","));
      logger.debug("bidderClients={}", bidderClients.toString());
      return bidderClients;
    }
  }
}