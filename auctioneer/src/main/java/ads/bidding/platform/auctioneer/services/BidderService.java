/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.services;

import ads.bidding.platform.auctioneer.models.AdBid;
import ads.bidding.platform.auctioneer.models.AdBidResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

  @Override
  public AdBidResponse bidForAnAd(String id, Map<String, String> attributes) throws IllegalStateException {
    final AdBid adBid = new AdBid(id, attributes);
    final ArrayList<AdBidResponse> adBids = new ArrayList<>();

    getAvailableBidders().forEach(bidderClientUrl -> {
      logger.debug("Sending bid to bidder service={} and adBid={}", bidderClientUrl, adBid);
      AdBidResponse adBidResponse = null;
      try {
        adBidResponse = sendBidRequest(bidderClientUrl, adBid);
      } catch (Exception e) {
        logger.error("Something went wrong when biding for at service={}", bidderClientUrl, e);
      }
      if (adBidResponse != null) {
        adBids.add(adBidResponse);
      }
    });

    return getHighestAdBidResponse(adBids);
  }

  /**
   * ToDo - handle errors, fail silently!
   *
   * @param urlToBidder
   * @param adBid
   * @return
   */
  private AdBidResponse sendBidRequest(final String urlToBidder, final AdBid adBid) {
    AdBidResponse adBidResponse = bidderRestTemplate.postForEntity(urlToBidder, new HttpEntity<>(adBid), AdBidResponse.class).getBody();
    logger.debug("adBidResponse={}", adBidResponse);
    return adBidResponse;
  }

  private AdBidResponse getHighestAdBidResponse(final ArrayList<AdBidResponse> adBids) throws IllegalStateException {
    return adBids.stream().max(Comparator.comparing(AdBidResponse::getBid)).orElseThrow(() -> new IllegalStateException("No bid found!"));
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
