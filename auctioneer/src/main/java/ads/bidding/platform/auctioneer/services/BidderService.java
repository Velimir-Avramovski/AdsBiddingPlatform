/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.services;

import ads.bidding.platform.auctioneer.exceptions.AdBidRequestException;
import ads.bidding.platform.auctioneer.exceptions.EmptyAdBidsExceptions;
import ads.bidding.platform.auctioneer.exceptions.ErrorCode;
import ads.bidding.platform.auctioneer.models.AdBid;
import ads.bidding.platform.auctioneer.models.AdBidResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * ToDo
 */
@Service
@RequiredArgsConstructor
public class BidderService implements Bidder {

  private final Logger logger = LoggerFactory.getLogger(BidderService.class);

  @Value("#{'${bidders}'.split('\\s*,\\s*')}")
  private List<String> biddersList;

  private final RestTemplate bidderRestTemplate;

  /**
   * ToDo
   *
   * @param id
   * @param attributes
   * @return
   * @throws IllegalStateException
   */
  @Override
  public AdBidResponse bidForAnAd(String id, Map<String, String> attributes) throws EmptyAdBidsExceptions {
    final ArrayList<AdBidResponse> adBids = new ArrayList<>();
    final AdBid adBidRequest = new AdBid(id, attributes);

    getAvailableBidders().forEach(bidderClientUrl -> {
      logger.debug("Sending bid to bidder service={} and adBid={}", bidderClientUrl, adBidRequest);
      AdBidResponse adBidResponse = null;
      // We want to continue getting ads even if one bid request fails!
      try {
        adBidResponse = sendBidRequest(bidderClientUrl, adBidRequest);
      } catch (AdBidRequestException e) {
        logger.error("Something went wrong when biding for at service={}", bidderClientUrl, e);
      }
      if (adBidResponse != null) {
        adBids.add(adBidResponse);
      }
    });

    return getHighestAdBidResponse(adBids)
        .orElseThrow(() -> new EmptyAdBidsExceptions("No valid ad bids found!", ErrorCode.AD_BID_REQUEST_FAILED));
  }

  /**
   * @param urlToBidder
   * @param adBid
   * @return
   * @throws AdBidRequestException
   */
  private AdBidResponse sendBidRequest(final String urlToBidder, final AdBid adBid) throws AdBidRequestException {
    AdBidResponse adBidResponse;
    try {
      adBidResponse = bidderRestTemplate.postForEntity(urlToBidder, new HttpEntity<>(adBid), AdBidResponse.class).getBody();
    } catch (Exception e) {
      throw new AdBidRequestException("Bidding for an ad failed!", e, ErrorCode.AD_BID_REQUEST_FAILED);
    }
    logger.debug("adBidResponse={}", adBidResponse);
    return adBidResponse;
  }

  private Optional<AdBidResponse> getHighestAdBidResponse(final ArrayList<AdBidResponse> adBids) {
    return adBids.stream().max(Comparator.comparing(AdBidResponse::getBid));
  }

  /**
   * ToDo
   *
   * @return
   * @throws Exception
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
