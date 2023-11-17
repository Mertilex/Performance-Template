package computerdatabase;

import simulations.Configs.GlobalConfig;
import simulations.FrameworkCore.HttpDefaults;
import simulations.Feeders.AccessTokensFeeder;
import simulations.Feeders.CsvFeeder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This sample is based on our official tutorials:
 * <ul>
 *   <li><a href="https://gatling.io/docs/gatling/tutorials/quickstart">Gatling quickstart tutorial</a>
 *   <li><a href="https://gatling.io/docs/gatling/tutorials/advanced">Gatling advanced tutorial</a>
 * </ul>
 */
public class ComputerDatabaseSimulation extends Simulation {

    ChainBuilder searchForComputer =
        exec(AccessTokensFeeder.feedAccessTokens)
        .exec(CsvFeeder.feedSearchCriteria)
        .exec(HttpDefaults.baseGet("/")) //Home
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
            .check(status().is(200))
        )
        .pause(GlobalConfig.scenarioPauses);

    ChainBuilder browseForComputer =
        repeat(4, "i").on(
            exec(
                HttpDefaults.baseGet("/computers?p=#{i}") //Page #{i}
            ).pause(GlobalConfig.scenarioPauses)
        );

    ChainBuilder editComputerWithRandomFailure =
        tryMax(2)
            .on(
                exec(
                    HttpDefaults.baseGet("/computers/new")) //Form
                    .pause(GlobalConfig.scenarioPauses)
                    .exec(
                        HttpDefaults.basePost("/computers") //Post
                            .formParam("name", "Beautiful Computer")
                            .formParam("introduced", "2012-05-30")
                            .formParam("discontinued", "")
                            .formParam("company", "37")
                            .check(
                                status().is(
                                    // This intentionally causes random failures to show how it refrects to the report
                                    session -> 200 + ThreadLocalRandom.current().nextInt(2)
                                )
                            )
                    )
            )
            .exitHereIfFailed();

    ScenarioBuilder searchAndBrowseForComputer = scenario("Search and browse for computer").exec(
        searchForComputer
        ,browseForComputer
    );

    ScenarioBuilder searchAndBrowseAndEditComputer = scenario("Search, Browse and edit Computer with random failure").exec(
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
