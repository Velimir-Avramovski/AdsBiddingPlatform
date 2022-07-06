package ads.bidding.platform.auctioneer.services;

import ads.bidding.platform.auctioneer.models.AdBidResponse;

public interface Bidder {

  AdBidResponse bidForAnAd(final String id, final String[] attributes);

}
