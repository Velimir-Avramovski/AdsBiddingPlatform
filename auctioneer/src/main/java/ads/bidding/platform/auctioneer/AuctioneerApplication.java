/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class that runs our spring boot app (auctioneer service).
 */
@SpringBootApplication
public class AuctioneerApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuctioneerApplication.class, args);
  }

}
