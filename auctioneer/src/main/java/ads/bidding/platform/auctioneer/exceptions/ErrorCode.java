/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.exceptions;

/**
 * Enum representing our custom error codes.
 */
public enum ErrorCode {

  EMPTY_AD_BIDS(100),
  AD_BID_REQUEST_FAILED(101);

  private final int errorCode;

  /**
   * Enum constructor.
   *
   * @param errorCode error code as int.
   */
  ErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

}
