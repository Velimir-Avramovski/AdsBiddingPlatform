/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception that is thrown when ad bid request fails (request to a bidder service).
 */
public class AdBidRequestException extends Exception {

  private final Logger logger = LoggerFactory.getLogger(AdBidRequestException.class);

  /**
   * AdBidRequestException exception.
   *
   * @param message   message of the exception.
   * @param cause     cause (throwable).
   * @param errorCode custom error code.
   */
  public AdBidRequestException(final String message, final Throwable cause, final ErrorCode errorCode) {
    super(message, cause);
    logger.error("AdBidRequestException! message={} errorCode={}", message, errorCode);
  }

  /**
   * AdBidRequestException exception.
   *
   * @param message   message of the exception.
   * @param errorCode custom error code.
   */
  public AdBidRequestException(final String message, final ErrorCode errorCode) {
    super(message);
    logger.error("AdBidRequestException! message={} errorCode={}", message, errorCode);
  }

}
