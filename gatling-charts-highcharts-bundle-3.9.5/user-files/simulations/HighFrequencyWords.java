package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class HighFrequencyWords {
    public static ChainBuilder openHighFrequencyWords = exec(
            exec(http("Assesment Subsection - High Frequency Words - Request 1")
                .get("/api/lookup/all-entries/false"))
            .pause(1)
            .exec(http("Assesment Subsection - High Frequency Words - Request 2")
                .get("/api/lookup/all-sets"))
            .pause(1)
            .exec(http("Assesment Subsection - High Frequency Words - Request 3")
                .get("/api/lookup/terms"))
            .pause(1)
            .exec(http("Assesment Subsection - High Frequency Words - Request 4")
                .get("/api/lookup/phase/2"))
            .pause(1)
        );
}

