package ads.bidding.platform.auctioneer.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ToDo
 */
public class EmptyAdBidsExceptions extends Exception {

  private final Logger logger = LoggerFactory.getLogger(EmptyAdBidsExceptions.class);

  public EmptyAdBidsExceptions(final String message, final ErrorCode errorCode) {
    super(message);
    logger.error("EmptyAdBidsExceptions! message={} errorCode={}", message, errorCode);
  }

}
