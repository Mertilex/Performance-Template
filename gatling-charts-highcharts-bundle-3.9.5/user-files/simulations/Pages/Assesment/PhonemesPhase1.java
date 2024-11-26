package simulations.Pages.Assesment;

import simulations.Configs.GlobalConfig;
import static simulations.FrameworkCore.HttpDefaults.baseGet;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class PhonemesPhase1 {
    public static ChainBuilder openPhonemes_Phase_1 = exec(
        group("PhonemesPhase1 - Open phonemes phase 1").on(
            exec(baseGet("/api/lookup/pupil-by-entry/18535?include=false"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(baseGet("/api/lookup/all-entries/false"))
            .pause(GlobalConfig.scenarioPauses) 
            .exec(baseGet("/api/lookup/all-sets"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(baseGet("/api/lookup/phase/10"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(baseGet("/api/lookup/terms"))
            .pause(2) //TODO: GlobalConfig.scenarioPauses
        )
    );    
}
