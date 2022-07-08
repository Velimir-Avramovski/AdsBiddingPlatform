/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.services;

import ads.bidding.platform.auctioneer.exceptions.AdBidRequestException;
import ads.bidding.platform.auctioneer.exceptions.EmptyAdBidsExceptions;
import ads.bidding.platform.auctioneer.exceptions.ErrorCode;
import ads.bidding.platform.auctioneer.models.AdBidRequest;
import ads.bidding.platform.auctioneer.models.AdBidResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation that defines the behavior of a bidder service that should communicate with the actual bidders.
 */
@Service
@RequiredArgsConstructor
public class BidderService implements Bidder {

  private final Logger logger = LoggerFactory.getLogger(BidderService.class);

  @Value("#{'${bidders}'.split('\\s*,\\s*')}")
  private final List<String> biddersList;

  private final RestTemplate bidderRestTemplate;

  /**
   * Send bid requests to all bidder services.
   *
   * @param id         id of the ad.
   * @param attributes attributes.
   * @return winner AdBidResponse response.
   * @throws EmptyAdBidsExceptions this is thrown when no bids are returned from the bidder services.
   * @throws AdBidRequestException this is thrown when requests to bidder services failed.
   */
  @Override
  public AdBidResponse bidForAnAd(String id, Map<String, String> attributes) throws EmptyAdBidsExceptions, AdBidRequestException {
    final List<String> bidderServicesUrl = getAvailableBidders();
    final AtomicInteger bidderServiceRequestFailed = new AtomicInteger();
    final AdBidRequest adBidRequest = new AdBidRequest(id, attributes);

    // Get all bids for an ad.
    final ArrayList<AdBidResponse> adBids = getAllBidsForAnAd(bidderServicesUrl, bidderServiceRequestFailed, adBidRequest);

    // Check if all request actually failed to the bidders, if so throw proper exception.
    if (bidderServiceRequestFailed.get() == bidderServicesUrl.size()) {
      throw new AdBidRequestException("All requests to bidder services failed!", ErrorCode.EMPTY_AD_BIDS);
    }

    // Return the highest bid or throw an exception if no bid is found.
    return getHighestAdBidResponse(adBids)
        .orElseThrow(() -> new EmptyAdBidsExceptions("No valid ad bids found!", ErrorCode.EMPTY_AD_BIDS));
  }

  /**
   * Sends all the bids to the bidder services and returns an list of those responses.
   *
   * @param bidderServicesUrl          list of bidder urls.
   * @param bidderServiceRequestFailed counter of how much requests have failed.
   * @param adBidRequest               ad bid request object model.
   * @return ArrayList<AdBidResponse> list of ad bid responses.
   */
  private ArrayList<AdBidResponse> getAllBidsForAnAd(
      final List<String> bidderServicesUrl, final AtomicInteger bidderServiceRequestFailed, final AdBidRequest adBidRequest) {
    final ArrayList<AdBidResponse> adBids = new ArrayList<>();
    bidderServicesUrl.forEach(bidderClientUrl -> {
      logger.debug("Sending bid to bidder service={} and adBid={}", bidderClientUrl, adBidRequest);
      AdBidResponse adBidResponse = null;
      // We want to continue getting ad bids even if one bid request fails!
      try {
        adBidResponse = sendBidRequest(bidderClientUrl, adBidRequest);
      } catch (AdBidRequestException e) {
        bidderServiceRequestFailed.getAndIncrement();
        logger.error("Something went wrong when biding for at service={}", bidderClientUrl, e);
      }
      // If the bidder response is valid, add it to the candidate list.
      if (adBidResponse != null) {
        adBids.add(adBidResponse);
      }
    });
    return adBids;
  }

  /**
   * Send HTTP request to particular bidder service. ToDo - This could probably be moved in an other class
   *
   * @param urlToBidder  url to bidder service.
   * @param adBidRequest ad bid request.
   * @return AdBidResponse ad bid response.
   * @throws AdBidRequestException exception of the request fails for whatever reason.
   */
  private AdBidResponse sendBidRequest(final String urlToBidder, final AdBidRequest adBidRequest) throws AdBidRequestException {
    AdBidResponse adBidResponse;
    try {
      adBidResponse = bidderRestTemplate.postForEntity(urlToBidder, new HttpEntity<>(adBidRequest), AdBidResponse.class).getBody();
    } catch (Exception e) {
      throw new AdBidRequestException("Bidding for an ad failed!", e, ErrorCode.AD_BID_REQUEST_FAILED);
    }
    logger.debug("adBidResponse={}", adBidResponse);
    return adBidResponse;
  }

  /**
   * Get the maximum bid of them all.
   *
   * @param adBids all ad bids.
   * @return maximum ad bid (optional, might be null).
   */
  private Optional<AdBidResponse> getHighestAdBidResponse(final ArrayList<AdBidResponse> adBids) {
    return adBids.stream().max(Comparator.comparing(AdBidResponse::getBid));
  }

  /**
   * Gets the available bidder services url.
   *
   * @return list of bidder services.
   */
  public List<String> getAvailableBidders() {
    if (biddersList == null || biddersList.isEmpty()) {
      logger.debug("Configuration for bidders service is not available!");
      return Collections.emptyList();
    } else {
      logger.debug("bidderClients={}", biddersList);
      return biddersList;
    }
  }

}
