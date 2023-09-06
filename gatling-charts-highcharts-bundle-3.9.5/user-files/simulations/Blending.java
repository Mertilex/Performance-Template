package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class Blending {
    public static ChainBuilder openBlending = exec(
        exec(http("Assesment Subsection - Blending - Request 1")
            .get("/api/lookup/phase/4"))
        .pause(1)
    ); 
}

