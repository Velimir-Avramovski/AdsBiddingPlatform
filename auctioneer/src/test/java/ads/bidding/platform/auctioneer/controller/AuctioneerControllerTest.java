/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import ads.bidding.platform.auctioneer.exceptions.AdBidRequestException;
import ads.bidding.platform.auctioneer.exceptions.EmptyAdBidsExceptions;
import ads.bidding.platform.auctioneer.exceptions.ErrorCode;
import ads.bidding.platform.auctioneer.models.AdBidResponse;
import ads.bidding.platform.auctioneer.services.BidderService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class AuctioneerControllerTest {

  private AuctioneerController auctioneerController;

  @Mock
  private BidderService bidderService;

  @BeforeEach
  void setup() {
    auctioneerController = new AuctioneerController(bidderService);
  }

  @Test
  public void getAd_Successfully() throws EmptyAdBidsExceptions, AdBidRequestException {
    // test data
    final String id = "123";
    final Map<String, String> attributes = new HashMap<>();
    attributes.put("a", "1");
    attributes.put("b", "2");
    final AdBidResponse adBidResponseMock = new AdBidResponse("123", 500L, "a:$price");

    // when
    when(bidderService.bidForAnAd(anyString(), any())).thenReturn(adBidResponseMock);

    // then
    final ResponseEntity<String> adBidResponseReturned = auctioneerController.getAd(id, attributes);

    // expected
    Assertions.assertNotNull(adBidResponseReturned);
  }

  @Test
  public void getAd_EmptyAdBidsExceptions() throws EmptyAdBidsExceptions, AdBidRequestException {
    // test data
    final String id = "123";
    final Map<String, String> attributes = new HashMap<>();
    attributes.put("a", "1");

    // when
    when(bidderService.bidForAnAd(anyString(), any()))
        .thenThrow(new EmptyAdBidsExceptions("No valid ad bids found!", ErrorCode.EMPTY_AD_BIDS));

    // then
    Throwable exception = assertThrows(EmptyAdBidsExceptions.class, () -> auctioneerController.getAd(id, attributes));

    // expected
    assertEquals("No valid ad bids found!", exception.getMessage());
  }

  @Test
  public void getAd_AdBidRequestException() throws EmptyAdBidsExceptions, AdBidRequestException {
    // test data
    final String id = "123";
    final Map<String, String> attributes = new HashMap<>();
    attributes.put("a", "1");

    // when
    when(bidderService.bidForAnAd(anyString(), any()))
        .thenThrow(new AdBidRequestException("All requests to bidder services failed!", ErrorCode.EMPTY_AD_BIDS));

    // then
    Throwable exception = assertThrows(AdBidRequestException.class, () -> auctioneerController.getAd(id, attributes));

    // expected
    assertEquals("All requests to bidder services failed!", exception.getMessage());
  }

}