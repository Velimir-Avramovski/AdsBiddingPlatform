/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.models;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model of an ad bid object. Example usage is as incoming request payload.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdBidRequest {

  private String id;

  private Map<String, String> attributes;

}
