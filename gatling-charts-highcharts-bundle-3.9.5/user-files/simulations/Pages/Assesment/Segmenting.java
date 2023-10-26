package simulations.Pages.Assesment;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class Segmenting {
    public static ChainBuilder openSegmenting = exec(
        exec(http("Assesment Subsection - Segmenting - Request 1")
            .get("/api/lookup/all-entries/false"))
        .pause(1)
        .exec(http("Assesment Subsection - Segmenting - Request 2")
            .get("/api/lookup/all-sets"))
        .pause(1)
         .exec(http("Assesment Subsection - Segmenting - Request 3")
            .get("/api/lookup/terms"))
        .pause(1)
        .exec(http("Assesment Subsection - Segmenting - Request 4")
            .get("/api/lookup/phase/5"))
        .pause(1)
    );
}

