package simulations.Pages.Assesment;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class ScreeningCheck {
    public static ChainBuilder openScreeningCheck = exec(
        exec(http("Assesment Subsection - Screening Check - Request 1")
            .get("/api/lookup/phase/3"))
        .pause(1)
    );
}

