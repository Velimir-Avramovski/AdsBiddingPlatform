/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.controller;

import ads.bidding.platform.auctioneer.models.AdAuctioneerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ToDo
 */
@RestController()
@RequestMapping("v1")
public class AuctioneerRestApi {

  Logger logger = LoggerFactory.getLogger(AuctioneerRestApi.class);

  /**
   * ToDo
   */
  @GetMapping(path = "/ad/{id}/{attributes}", produces = {MediaType.APPLICATION_JSON_VALUE})
  AdAuctioneerResponse getAd(@PathVariable String id, @PathVariable String[] attributes) {
    logger.debug("Received getAd request with id={} and attributes={}", id, attributes);
    return null;
  }

}
