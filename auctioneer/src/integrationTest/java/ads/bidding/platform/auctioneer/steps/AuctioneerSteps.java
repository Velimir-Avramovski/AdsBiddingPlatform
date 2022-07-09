package ads.bidding.platform.auctioneer.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AuctioneerSteps {

  private final String appUrl = "http://localhost:8080/v1/ad/";

  private ResponseEntity<String> returnedEntityResult;

  @Given("I send ad get request with id {int} and attributes {string}")
  public void givenAccountBalance(int id, String attributes) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(HttpHeaders.AUTHORIZATION, "Basic dmVsaW1pcjoxMjMzMjE=");
    HttpEntity<String> request = new HttpEntity<>(httpHeaders);
    returnedEntityResult = restTemplate.exchange(appUrl + id + "?" + attributes, HttpMethod.GET, request, String.class);
  }

  @Then("I validate that status code is {int} and result is {string}")
  public void validateResult(int code, String result) {
    Assertions.assertEquals(code, returnedEntityResult.getStatusCode().value());
    Assertions.assertEquals(result, returnedEntityResult.getBody());
  }

}