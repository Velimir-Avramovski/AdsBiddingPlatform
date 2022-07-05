/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.configuration;

import ads.bidding.platform.auctioneer.models.AdBid;
import ads.bidding.platform.auctioneer.models.AdBidResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BidderClientConfiguration {

  private final Logger logger = LoggerFactory.getLogger(BidderClientConfiguration.class);

  /**
   * ToDo
   *
   * @param urlToBidder
   * @return
   */
  @Lazy
  @Bean()
  public ResponseEntity<AdBidResponse> getBidderClient(final String urlToBidder, final AdBid adBid) {
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<AdBid> requestBody = new HttpEntity<>(adBid);
    return restTemplate.postForEntity(urlToBidder, requestBody, AdBidResponse.class);
  }

}

