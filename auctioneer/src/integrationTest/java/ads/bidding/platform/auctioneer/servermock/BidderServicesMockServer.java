package ads.bidding.platform.auctioneer.servermock;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import ads.bidding.platform.auctioneer.models.AdBidResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class BidderServicesMockServer {

  WireMockServer wireMockServer_1 = new WireMockServer(wireMockConfig().port(8081));
  WireMockServer wireMockServer_2 = new WireMockServer(wireMockConfig().port(8082));
  WireMockServer wireMockServer_3 = new WireMockServer(wireMockConfig().port(8083));

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void startServers() {
    wireMockServer_1.start();
    try {
      setupWireMockServer(wireMockServer_1, "123", 500L, "a:$price");
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    wireMockServer_2.start();
    try {
      setupWireMockServer(wireMockServer_2, "123", 200L, "c:$price");
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    wireMockServer_3.start();
    try {
      setupWireMockServer(wireMockServer_3, "123", 400L, "d:$price");
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  private void setupWireMockServer(WireMockServer wireMockServer, String id, Long bid, String content) throws JsonProcessingException {
    final AdBidResponse adBidResponse = new AdBidResponse(id, bid, content);
    wireMockServer.stubFor(post("/")
        .willReturn(ok()
            .withHeader("Content-Type", "application/json")
            .withBody(objectMapper.writeValueAsString(adBidResponse))));
  }

  @After
  public void stopServers() {
    wireMockServer_1.stop();
    wireMockServer_2.stop();
    wireMockServer_3.stop();
  }

}
