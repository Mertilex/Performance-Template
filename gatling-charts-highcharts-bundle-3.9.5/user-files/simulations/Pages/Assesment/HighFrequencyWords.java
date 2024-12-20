package simulations.Pages.Assesment;

import simulations.Configs.GlobalConfig;
import static simulations.FrameworkCore.HttpDefaults.baseGet;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class HighFrequencyWords {
    public static ChainBuilder openHighFrequencyWords = exec(
        group("HighFrequencyWords - Open high frequency words").on(
            exec(baseGet("/api/lookup/all-entries/false"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(baseGet("/api/lookup/all-sets"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(baseGet("/api/lookup/terms"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(baseGet("/api/lookup/phase/2"))
            .pause(GlobalConfig.scenarioPauses)
        )
    );
}

