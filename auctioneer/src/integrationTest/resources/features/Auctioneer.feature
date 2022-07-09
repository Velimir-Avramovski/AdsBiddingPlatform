Feature: Auctioneer Integration Testing

  Scenario: Send an ad get request to Auctioneer service
    Given I send ad get request with id 123 and attributes "a=5&c=1"
    Then I validate that status code is 200 and result is "a:500"