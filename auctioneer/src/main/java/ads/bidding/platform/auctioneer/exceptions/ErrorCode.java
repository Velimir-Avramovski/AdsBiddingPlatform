package ads.bidding.platform.auctioneer.exceptions;

/**
 * ToDo
 */
public enum ErrorCode {

  EMPTY_AD_BIDS(100),
  AD_BID_REQUEST_FAILED(101);

  private final int errorCode;

  ErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
}
