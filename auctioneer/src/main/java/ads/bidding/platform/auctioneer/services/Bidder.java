/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.services;

import ads.bidding.platform.auctioneer.exceptions.AdBidRequestException;
import ads.bidding.platform.auctioneer.exceptions.EmptyAdBidsExceptions;
import ads.bidding.platform.auctioneer.models.AdBidResponse;
import java.util.Map;

/**
 * Interface that defines the behavior of a bidder service that should communicate with the actual bidders.
 */
public interface Bidder {

  /**
   * Send bid requests to all bidder services.
   *
   * @param id         id of the ad.
   * @param attributes attributes.
   * @return winner AdBidResponse response.
   * @throws EmptyAdBidsExceptions this is thrown when no bids are returned from the bidder services.
   * @throws AdBidRequestException this is thrown when requests to bidder services failed.
   */
  AdBidResponse bidForAnAd(final String id, final Map<String, String> attributes) throws EmptyAdBidsExceptions, AdBidRequestException;

}
