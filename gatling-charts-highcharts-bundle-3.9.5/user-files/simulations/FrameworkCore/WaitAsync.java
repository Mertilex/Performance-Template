package simulations.FrameworkCore;

import java.util.concurrent.TimeUnit;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;
import simulations.Configs.GlobalConfig;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

// import io.gatling.core.structure.ChainBuilder;
import io.gatling.core.structure.PopulationBuilder;
import io.gatling.core.structure.ScenarioBuilder;
import io.gatling.core.structure.Loops;
import io.gatling.http.Predef.*;
import scala.concurrent.duration.Duration;

public class WaitAsync {
    private ChainBuilder waitBasedOnApiReponse() {
        return doWhileDuring("#{condition}", 5).on(
            exec(http("name").get("/"))
        );

        // return group("Wait for ASYNC call", () ->
        //         doWhileDuring(
        //                 s -> s("status").as(String.class).equals("DONE"),
        //                 Duration.create(10, TimeUnit.SECONDS),
        //                 exec(
        //                         pause(GlobalConfig.scenarioPauses),

        //                         //This is wait condition
        //                         HttpDefaults.baseGet("/")
        //                             .check(jsonPath("$.statusId").find().saveAs("status"))
        //                 )
        //         )
        // );
    }

    // private ChainBuilder waitBasedOnDbState() {
    //     //TODO
    // }

    // private ChainBuilder waitBasedOn3rdParty() {
    //     //TODO
    // }
}
