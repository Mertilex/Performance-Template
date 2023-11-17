package computerdatabase;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

import simulations.FrameworkCore.HttpDefaults;
import simulations.Feeders.AccessTokensFeeder;
import simulations.FrameworkCore.StatusCodes;

public class FirstSimulation extends Simulation {
    ChainBuilder homeScreen = exec(
        group("Open Specific Computer - Open Home Screen").on(
            exec(HttpDefaults.baseGetWithNoCache("/")
                .check(status().in(StatusCodes.getAcceptedStatusCodes()))))
    );

    ChainBuilder openSpecificComputer = exec(
        group("Open Specific Computer - Get Computer").on(
            exec(HttpDefaults.baseGetWithNoCache("/computers/501")
                .check(status().in(StatusCodes.getAcceptedStatusCodes()))))
    );

    ChainBuilder searchForMacbook = exec(
        group("Open Specific Computer - Search For Macbook").on(
            exec(HttpDefaults.baseGetWithNoCache("/computers?f=macbook")
                .check(status().in(StatusCodes.getAcceptedStatusCodes()))))
    );

    ScenarioBuilder openComputer = scenario("Search for computer")
        .exec(
            AccessTokensFeeder.feedAccessTokens
            ,homeScreen
            ,openSpecificComputer
    );

    ScenarioBuilder searchForMacbookComputer = scenario("Search for Macbook")
        .exec(
            AccessTokensFeeder.feedAccessTokens
            ,homeScreen
            ,openSpecificComputer
            ,searchForMacbook
    );

    {
        setUp(
            openComputer.injectOpen(atOnceUsers(1))
            ,searchForMacbookComputer.injectOpen(atOnceUsers(1))
            )
        .protocols(HttpDefaults.httpProtocol);
    }
}