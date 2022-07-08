/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model of an ad bid response object. Example usage is as response payload from the bidder services.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdBidResponse {

  private String id;

  private Long bid;

  private String content;

  @Override
  public String toString() {
    return content.split(":")[0] + ":" + bid;
  }

}
