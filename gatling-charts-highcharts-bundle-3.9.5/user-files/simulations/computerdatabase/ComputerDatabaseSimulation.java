package computerdatabase;

import simulations.Configs.GlobalConfig;
import simulations.FrameworkCore.HttpDefaults;
import simulations.FrameworkCore.StatusCodes;
import simulations.Feeders.AccessTokensFeeder;
import simulations.Feeders.CsvFeeder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.util.concurrent.ThreadLocalRandom;

public class ComputerDatabaseSimulation extends Simulation {

    ChainBuilder searchForComputer =
        group("Computer Database - Search for computer").on(
            exec(AccessTokensFeeder.feedAccessTokens)
            .exec(CsvFeeder.feedSearchCriteria)
            .exec(HttpDefaults.baseGet("/"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(
                HttpDefaults.baseGet("/computers?f=#{searchCriterion}")
                .check(
                    css("a:contains('#{searchComputerName}')", "href")
                    .saveAs("computerUrl"))
            )
            .pause(GlobalConfig.scenarioPauses)
            .exec(
                HttpDefaults.baseGet("#{computerUrl}")
                .check(status().in(StatusCodes.getAcceptedStatusCodes()))
            )
            .pause(GlobalConfig.scenarioPauses)
        );

    ChainBuilder browseForComputer =
        group("Computer Database - Browse for computer").on(
            repeat(4, "i").on(
                exec(
                    HttpDefaults.baseGet("/computers?p=#{i}")
                ).pause(GlobalConfig.scenarioPauses)
            )
        );

    ChainBuilder editComputerWithRandomFailure =
        group("Computer Database - Search, browse and edit Computer").on(
            tryMax(2)
                .on(
                    exec(
                        HttpDefaults.baseGet("/computers/new"))
                        .pause(GlobalConfig.scenarioPauses)
                        .exec(
                            HttpDefaults.basePost("/computers")
                                .formParam("name", "Beautiful Computer")
                                .formParam("introduced", "2012-05-30")
                                .formParam("discontinued", "")
                                .formParam("company", "37")
                                .check(
                                    status().is(
                                        // This intentionally causes random failures to show how it refrects to the report
                                        session -> 200 + ThreadLocalRandom.current().nextInt(2)
                                        //status().in(StatusCodes.getAcceptedStatusCodes())
                                    )
                                )
                        )
                )
                .exitHereIfFailed()
        );

    ScenarioBuilder searchAndBrowseForComputer = scenario("Search and browse for computer").exec(
        searchForComputer
        ,browseForComputer
    );

    ScenarioBuilder searchAndBrowseAndEditComputer = scenario("Search, browse and edit Computer with random failure").exec(
        searchForComputer
        ,browseForComputer
        ,editComputerWithRandomFailure
    );

    {
        setUp(
            searchAndBrowseForComputer.injectOpen(atOnceUsers(1))//rampUsers(10).during(10)),
            ,searchAndBrowseAndEditComputer.injectOpen(atOnceUsers(1))//rampUsers(2).during(10))
        ).protocols(HttpDefaults.httpProtocol);
    }
}
