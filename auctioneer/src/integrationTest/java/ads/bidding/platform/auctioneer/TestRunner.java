package ads.bidding.platform.auctioneer;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/IntegrationTest/resources/features",
    plugin = {"pretty", "html:target/cucumber"},
    monochrome = true
)
public class TestRunner {

}