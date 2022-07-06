package ads.bidding.platform.auctioneer.services;

import ads.bidding.platform.auctioneer.models.AdBidResponse;
import java.util.Map;

public interface Bidder {

  AdBidResponse bidForAnAd(final String id, final Map<String, String> attributes);

}
