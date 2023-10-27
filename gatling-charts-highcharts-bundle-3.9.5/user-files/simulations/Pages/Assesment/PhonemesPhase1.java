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
            exec(http("Assesment Subsection - Phonemes Phase 1 - Request 1")
                .get("/api/lookup/pupil-by-entry/18535?include=false"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(http("Assesment Subsection - Phonemes Phase 1 - Request 2")
                .get("/api/lookup/all-entries/false"))
            .pause(GlobalConfig.scenarioPauses) 
            .exec(http("Assesment Subsection - Phonemes Phase 1 - Request 3")
                .get("/api/lookup/all-sets"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(http("Assesment Subsection - Phonemes Phase 1 - Request 4")
                .get("/api/lookup/phase/10"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(http("Assesment Subsection - Phonemes Phase 1 - Request 5")
                .get("/api/lookup/terms"))
            .pause(2) //TODO: Magic number, why 2?
        )
    );    
}
