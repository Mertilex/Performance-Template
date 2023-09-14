package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class LetterNames {
    public static ChainBuilder openLetterNames = exec(
            exec(http("Assesment Subsection - Letter Names - Request 1")
                .get("/api/lookup/phase/8")
                .check(status().is(200)))
            .pause(1)   
        );
}