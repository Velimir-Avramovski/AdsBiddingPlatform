/*
  Copyrights to Velimir Avramovski, July 2022.
 */
package ads.bidding.platform.auctioneer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import ads.bidding.platform.auctioneer.exceptions.AdBidRequestException;
import ads.bidding.platform.auctioneer.exceptions.EmptyAdBidsExceptions;
import ads.bidding.platform.auctioneer.models.AdBidResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class BidderServiceTest {

  private BidderService bidderService;

  @Mock
  private RestTemplate bidderRestTemplate;

  @BeforeEach
  void setup() {
    List<String> biddersList = List.of("http://localhost:8081", "http://localhost:8082", "http://localhost:8083");
    bidderService = new BidderService(biddersList, bidderRestTemplate);
  }


  @Test
  public void bidForAnAd_Successfully() throws EmptyAdBidsExceptions, AdBidRequestException {
    // test data
    final String id = "123";
    final Map<String, String> attributes = new HashMap<>();
    attributes.put("a", "1");
    attributes.put("b", "2");
    final ResponseEntity<Object> adBidResponseMock_1 =
        new ResponseEntity<>(new AdBidResponse("123", 500L, "a:$price"), HttpStatus.OK);
    final ResponseEntity<Object> adBidResponseMock_2 =
        new ResponseEntity<>(new AdBidResponse("123", 750L, "b:$price"), HttpStatus.OK);
    final ResponseEntity<Object> adBidResponseMock_3 =
        new ResponseEntity<>(new AdBidResponse("123", 250L, "c:$price"), HttpStatus.OK);

    // when
    when(bidderRestTemplate.postForEntity(anyString(), any(), any()))
        .thenReturn(adBidResponseMock_1)
        .thenReturn(adBidResponseMock_2)
        .thenReturn(adBidResponseMock_3);

    // then
    final AdBidResponse adBidResponse = bidderService.bidForAnAd(id, attributes);

    // expected
    Assertions.assertNotNull(adBidResponse);
    Assertions.assertEquals(750L, adBidResponse.getBid());
  }

  @Test
  public void bidForAnAd_AdBidRequestException() throws AdBidRequestException {
    // test data
    final String id = "123";
    final Map<String, String> attributes = new HashMap<>();
    attributes.put("a", "1");
    attributes.put("b", "2");

    // when
    when(bidderRestTemplate.postForEntity(anyString(), any(), any()))
        .thenReturn(null);

    // then
    Throwable exception = assertThrows(AdBidRequestException.class, () -> bidderService.bidForAnAd(id, attributes));

    // expected
    assertEquals("All requests to bidder services failed!", exception.getMessage());
  }

  @Test
  public void bidForAnAd_EmptyAdBidsExceptions() throws EmptyAdBidsExceptions {
    // test data
    final String id = "123";
    final Map<String, String> attributes = new HashMap<>();
    attributes.put("a", "1");
    attributes.put("b", "2");
    final ResponseEntity<Object> adBidResponseMock_1 =
        new ResponseEntity<>(null, HttpStatus.OK);

    // when
    when(bidderRestTemplate.postForEntity(anyString(), any(), any()))
        .thenReturn(adBidResponseMock_1);

    // then
    Throwable exception = assertThrows(EmptyAdBidsExceptions.class, () -> bidderService.bidForAnAd(id, attributes));

    // expected
    assertEquals("No valid ad bids found!", exception.getMessage());
  }

}