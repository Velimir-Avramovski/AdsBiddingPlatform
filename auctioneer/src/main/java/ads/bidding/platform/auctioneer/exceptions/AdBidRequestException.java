/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ToDo
 */
public class AdBidRequestException extends Exception {

  private final Logger logger = LoggerFactory.getLogger(AdBidRequestException.class);

  public AdBidRequestException(final String message, final Throwable cause, final ErrorCode errorCode) {
    super(message, cause);
    logger.error("AdBidRequestException! message={} errorCode={}", message, errorCode);
  }

  public AdBidRequestException(final String message, final ErrorCode errorCode) {
    super(message);
    logger.error("AdBidRequestException! message={} errorCode={}", message, errorCode);
  }

}
