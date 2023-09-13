package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class Phonemes {
    public static ChainBuilder openPhonemes = exec(
            exec(http("Assesment Subsection - Phonemes - Request 1")
                .get("/api/lookup/pupil-by-entry/18535?include=false"))
            .pause(1)
            .exec(http("Assesment Subsection - Phonemes - Request 2")
                .get("/api/lookup/all-entries/false"))
            .pause(1) 
            .exec(http("Assesment Subsection - Phonemes - Request 3")
                .get("/api/lookup/all-sets"))
            .pause(1)
            .exec(http("Assesment Subsection - Phonemes - Request 4")
                .get("/api/lookup/phase/1"))
            .pause(1)
            .exec(http("Assesment Subsection - Phonemes - Request 5")
                .get("/api/lookup/terms"))
            .pause(1)
        );
      
}

