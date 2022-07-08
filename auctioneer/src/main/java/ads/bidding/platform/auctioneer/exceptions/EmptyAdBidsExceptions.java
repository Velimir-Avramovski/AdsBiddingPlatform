/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception that is thrown when no ad bids are returned from bidder services.
 */
public class EmptyAdBidsExceptions extends Exception {

  private final Logger logger = LoggerFactory.getLogger(EmptyAdBidsExceptions.class);

  /**
   * EmptyAdBidsExceptions exception.
   *
   * @param message   message of the exception.
   * @param errorCode custom error code.
   */
  public EmptyAdBidsExceptions(final String message, final ErrorCode errorCode) {
    super(message);
    logger.error("EmptyAdBidsExceptions! message={} errorCode={}", message, errorCode);
  }

}
