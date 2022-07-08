/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.controller;

import ads.bidding.platform.auctioneer.exceptions.AdBidRequestException;
import ads.bidding.platform.auctioneer.exceptions.EmptyAdBidsExceptions;
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
 * Rest controller that handles ad requests.
 */
@RestController()
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuctioneerController {

  private final Logger logger = LoggerFactory.getLogger(AuctioneerController.class);

  private final BidderService bidderService;

  /**
   * This endpoint fetches an ad for a given id and attributes. Ad request is send to the bidder services and then the highest one is
   * returned.
   *
   * @param id         - ad id.
   * @param attributes - ad attributes.
   * @return AdBidResponse object.
   * @throws EmptyAdBidsExceptions this is thrown when no bids are returned from the bidder services.
   * @throws AdBidRequestException this is thrown when requests to bidder services failed.
   */
  @GetMapping(path = "/ad/{id}", produces = {MediaType.TEXT_PLAIN_VALUE})
  ResponseEntity<AdBidResponse> getAd(@PathVariable String id, @RequestParam Map<String, String> attributes)
      throws EmptyAdBidsExceptions, AdBidRequestException {
    logger.debug("Received getAd request with id={} and attributes={}", id, attributes);
    final AdBidResponse adBidResponse = bidderService.bidForAnAd(id, attributes);
    return new ResponseEntity<>(adBidResponse, HttpStatus.OK);
  }

}
