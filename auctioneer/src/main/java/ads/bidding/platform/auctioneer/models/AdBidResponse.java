/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ToDo
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
