/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.services;

import ads.bidding.platform.auctioneer.models.AdBid;
import ads.bidding.platform.auctioneer.models.AdBidResponse;
import ads.bidding.platform.auctioneer.utils.CommonUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * ToDo
 */
@Service
@AllArgsConstructor
public class BidderService implements Bidder {

  private final Logger logger = LoggerFactory.getLogger(BidderService.class);

  @Autowired
  private final CommonUtils commonUtils;

  @Override
  public AdBidResponse bidForAnAd(String id, String[] attributes) {
    final ArrayList<AdBidResponse> adBids = new ArrayList<>();
    try {
      commonUtils.getAvailableBidders().forEach(bidderClient -> {
        final String bidderUrl = bidderClient.trim();
        logger.debug("Sending bid to bidder service={}", bidderUrl);
        final AdBid adBid = buildAdBid(id, attributes);
        logger.debug("Sending adBid={}", adBid);
        final AdBidResponse adBidResponse = sendBidRequest(bidderUrl, adBid);
        adBids.add(adBidResponse);
      });
    } catch (Exception e) {
      logger.error("Something went wrong when biding for an ad!", e);
    }
    return getHighestAdBidResponse(adBids);
  }

  private AdBid buildAdBid(String id, String[] attributes) {
    Map<String, String> attributesMap = new HashMap<>();
    for (String attribute : attributes) {
      final String[] keyValuePair = attribute.split("=");
      attributesMap.put(keyValuePair[0], keyValuePair[1]);
    }
    return new AdBid(id, attributesMap);
  }

  /**
   * ToDo - handle errors!
   *
   * @param urlToBidder
   * @param adBid
   * @return
   */
  private AdBidResponse sendBidRequest(final String urlToBidder, final AdBid adBid) {
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<AdBid> requestBody = new HttpEntity<>(adBid);
    AdBidResponse adBidResponse = restTemplate.postForEntity(urlToBidder, requestBody, AdBidResponse.class).getBody();
    logger.debug("adBidResponse={}", adBidResponse);
    return adBidResponse;
  }

  private AdBidResponse getHighestAdBidResponse(final ArrayList<AdBidResponse> adBids) {
    return adBids.stream().max(Comparator.comparing(AdBidResponse::getBid)).orElse(null);
  }

}
