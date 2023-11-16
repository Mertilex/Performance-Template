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
    // FeederBuilder<String> feeder = csv("search.csv").random();

    ChainBuilder search =
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

    // Repeat is a loop resolved at RUNTIME
    ChainBuilder browse =
        // Note how we force the counter name, so we can reuse it
        repeat(4, "i").on(
            exec(
                http("Page #{i}")
                    .get("/computers?p=#{i}")
            ).pause(GlobalConfig.scenarioPauses)
        );

    // Note we should be using a feeder here
    // Let's demonstrate how we can retry: let's make the request fail randomly and retry a given
    // number of times
    ChainBuilder edit =
        // Let's try at max 2 times
        tryMax(2)
            .on(
                exec(
                    http("Form")
                        .get("/computers/new")
                )
                    .pause(GlobalConfig.scenarioPauses)
                    .exec(
                        http("Post")
                            .post("/computers")
                            .formParam("name", "Beautiful Computer")
                            .formParam("introduced", "2012-05-30")
                            .formParam("discontinued", "")
                            .formParam("company", "37")
                            .check(
                                status().is(
                                    // We do a check on a condition that's been customized with
                                    // a lambda. It will be evaluated every time a user executes
                                    // the request.
                                    session -> 200 + ThreadLocalRandom.current().nextInt(2)
                                )
                            )
                    )
            )
            // If the chain didn't finally succeed, have the user exit the whole scenario
            .exitHereIfFailed();

    ScenarioBuilder users = scenario("Users").exec(
        search
        ,browse
    );

    ScenarioBuilder admins = scenario("Admins").exec(
        search
        ,browse
        ,edit
    );

    {
        setUp(
            users.injectOpen(atOnceUsers(1))//rampUsers(10).during(10)),
            ,admins.injectOpen(atOnceUsers(1))//rampUsers(2).during(10))
        ).protocols(HttpDefaults.httpProtocol);
    }
}
