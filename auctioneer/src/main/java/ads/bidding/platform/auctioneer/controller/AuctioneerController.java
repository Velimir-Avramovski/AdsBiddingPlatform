/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.controller;

import ads.bidding.platform.auctioneer.models.AdBidResponse;
import ads.bidding.platform.auctioneer.services.BidderService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ToDo
 */
@RestController()
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuctioneerController {

  private final Logger logger = LoggerFactory.getLogger(AuctioneerController.class);

  private final BidderService bidderService;

  /**
   * ToDo
   */
  @GetMapping(path = "/ad/{id}", produces = {MediaType.TEXT_PLAIN_VALUE})
  ResponseEntity<String> getAd(@PathVariable String id, @RequestParam Map<String, String> attributes) {
    logger.debug("Received getAd request with id={} and attributes={}", id, attributes);
    final AdBidResponse adBidResponse = bidderService.bidForAnAd(id, attributes);
    return new ResponseEntity<>(adBidResponse.toString(), null, HttpStatus.OK);
  }

}
