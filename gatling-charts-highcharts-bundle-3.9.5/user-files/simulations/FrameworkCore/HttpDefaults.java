package simulations.FrameworkCore;

import simulations.Configs.GlobalConfig;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class HttpDefaults extends Simulation {

    private static FeederBuilder<String> feeder = csv("accessTokens.csv").random();

    public static ChainBuilder feedAccessTokens = exec(
        feed(feeder)
    ); 

    public static HttpProtocolBuilder httpProtocol = http
        .baseUrl(GlobalConfig.appUrl)
        .inferHtmlResources(
            AllowList(),
            DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
        .authorizationHeader("Bearer #{accessToken}")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("en-US,en;q=0.7,pl;q=0.3")
        .upgradeInsecureRequestsHeader("1")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.0");

        //   public static HttpProtocolBuilder baseGet(String url) = http //httpProtocol
        //     .get(url)
        //     .authorizationHeader("Bearer #{accessToken}");

}
