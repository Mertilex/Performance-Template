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

public class HttpDefaults {
    public static HttpProtocolBuilder httpProtocol = http
        .baseUrl(GlobalConfig.appUrl)
        .inferHtmlResources(
            AllowList(),
            DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("en-US,en;q=0.7,pl;q=0.3")
        .upgradeInsecureRequestsHeader("1")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.0");
    
    private static Map<CharSequence, String> headersNoCache = Map.ofEntries(
        Map.entry("Cache-Control", "no-cache"),
        Map.entry("DNT", "1"),
        Map.entry("Pragma", "no-cache"));

    public static HttpRequestActionBuilder getNoAuth(String url)
    {
        return http(url)
                .get(url);
    };
    
        public static HttpRequestActionBuilder baseGet(String url)
    {
        return getNoAuth(url)
                .header("Authorization", "Bearer #{accessToken}");
    };

    public static HttpRequestActionBuilder baseGetWithNoCache(String url)
    {
        return http(url)
                .get(url)
                .headers(headersNoCache)
                .header("Authorization", "Bearer #{accessToken}");
    };

    public static HttpRequestActionBuilder postNoAuth(String url)
    {
        return http(url)
                .post(url);
    };

    public static HttpRequestActionBuilder basePost(String url)
    {
        return postNoAuth(url)
                .header("Authorization", "Bearer #{accessToken}");
    };

    public static HttpRequestActionBuilder postContentJson(String url)
    {
        return basePost(url)
                .header("content-type", "application/json");
    };
}