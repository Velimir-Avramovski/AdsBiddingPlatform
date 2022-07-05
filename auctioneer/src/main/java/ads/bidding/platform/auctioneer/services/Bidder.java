package ads.bidding.platform.auctioneer.services;

import ads.bidding.platform.auctioneer.models.AdBid;
import ads.bidding.platform.auctioneer.models.AdBidResponse;

public interface Bidder {

  AdBidResponse bidForAnAd(final AdBid adBid);

}
