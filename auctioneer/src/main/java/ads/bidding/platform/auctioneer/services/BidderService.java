/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.services;

import ads.bidding.platform.auctioneer.models.AdBid;
import ads.bidding.platform.auctioneer.models.AdBidResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * ToDo
 */
@Service
public class BidderService implements Bidder{

  private final Logger logger = LoggerFactory.getLogger(BidderService.class);

  @Autowired
  private final

  @Override
  public AdBidResponse bidForAnAd(AdBid adBid) {
    return null;
  }

  private ResponseEntity<AdBidResponse> sendBidRequest(final String urlToBidder, final AdBid adBid) {
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<AdBid> requestBody = new HttpEntity<>(adBid);
    return restTemplate.postForEntity(urlToBidder, requestBody, AdBidResponse.class);
  }

}
