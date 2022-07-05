/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.models;

import java.io.Serializable;
import lombok.Data;

/**
 * ToDo
 */
@Data
public class AdAuctioneerResponse implements Serializable {

  private String id;

  private String bid;

  private String content;

  @Override
  public String toString() {
    return content.split(":")[0] + ":" + bid;
  }

}
