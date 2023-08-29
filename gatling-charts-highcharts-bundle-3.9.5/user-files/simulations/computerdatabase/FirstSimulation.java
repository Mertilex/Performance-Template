package computerdatabase;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class FirstSimulation extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
      .baseUrl("http://computer-database.gatling.io")
      .inferHtmlResources(AllowList(),
          DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2",
              ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
      .acceptEncodingHeader("gzip, deflate")
      .acceptLanguageHeader("en-US,en;q=0.7,pl;q=0.3")
      .upgradeInsecureRequestsHeader("1")
      .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.0");

  private Map<CharSequence, String> headers_1 = Map.ofEntries(
      Map.entry("Cache-Control", "no-cache"),
      Map.entry("DNT", "1"),
      Map.entry("Pragma", "no-cache"));

  ChainBuilder homeScreen = exec(
      http("Home screen")
          .get("/"));

  ChainBuilder openSpecificComputer = exec(
      http("Open specific computer")
          .get("/computers/501")
          .headers(headers_1));

  ChainBuilder searchForComputer = exec(
      http("Search for computer")
          .get("/computers?f=macbook"));

  ScenarioBuilder scenario1 = scenario("First Scenario")
      .exec(homeScreen, openSpecificComputer);

  ScenarioBuilder scenario2 = scenario("Second Scenario")
      .exec(homeScreen, openSpecificComputer, searchForComputer);
  {
    setUp(
        scenario1.injectOpen(atOnceUsers(10)),
        scenario2.injectOpen(atOnceUsers(5)))
        .protocols(httpProtocol);
  }
}
