/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.controller;

import ads.bidding.platform.auctioneer.models.AdBidResponse;
import ads.bidding.platform.auctioneer.services.BidderService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ToDo
 */
@RestController()
@RequestMapping("/v1")
@AllArgsConstructor
public class AuctioneerController {

  private final Logger logger = LoggerFactory.getLogger(AuctioneerController.class);

  @Autowired
  private final BidderService bidderService;

  /**
   * ToDo
   */
  @GetMapping(path = "/ad/{id}/{attributes}", produces = {MediaType.TEXT_PLAIN_VALUE})
  ResponseEntity<String> getAd(@PathVariable String id, @PathVariable String[] attributes) {
    logger.debug("Received getAd request with id={} and attributes={}", id, attributes);

    final AdBidResponse adBidResponse = bidderService.bidForAnAd(id, attributes);

    return new ResponseEntity<>(adBidResponse.toString(), null, HttpStatus.OK);
  }

}
