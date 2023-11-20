package simulations.Pages.Assesment;

import simulations.Configs.GlobalConfig;
import static simulations.FrameworkCore.HttpDefaults.baseGet;
import simulations.FrameworkCore.StatusCodes;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class LetterNames {
    public static ChainBuilder openLetterNames = exec(
        group("LetterNames - Open letter names").on(
            exec(baseGet("/api/lookup/phase/8")
                .check(status().in(StatusCodes.getAcceptedStatusCodes())))
            .pause(GlobalConfig.scenarioPauses)
        )
    );
}