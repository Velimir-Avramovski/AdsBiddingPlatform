/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.models;

import java.util.Map;
import lombok.Data;

/**
 * ToDo
 */
@Data
public class AdBid {

  private String id;

  private Map<String, String> attributes;

}
