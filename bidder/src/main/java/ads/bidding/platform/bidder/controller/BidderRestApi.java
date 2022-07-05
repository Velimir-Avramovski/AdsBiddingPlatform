/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.bidder.controller;

import ads.bidding.platform.bidder.models.AdAuctioneerResponse;
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
public class BidderRestApi {

  private final Logger logger = LoggerFactory.getLogger(BidderRestApi.class);

  /**
   * ToDo
   */
  @GetMapping(path = "/ad/{id}/{attributes}", produces = {MediaType.APPLICATION_JSON_VALUE})
  AdAuctioneerResponse getAd(@PathVariable String id, @PathVariable String[] attributes) {
    logger.debug("Received getAd request with id={} and attributes={}", id, attributes);
    return null;
  }

}
