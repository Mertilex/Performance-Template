package simulations.Pages.Assesment;

import simulations.Configs.GlobalConfig;
import simulations.Feeders.DbFeeder;
import simulations.FrameworkCore.HttpDefaults;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class Phonemes {
    public static ChainBuilder openPhonemes = exec(
        group("Assesment - Open Phonemes section").on(
            exec(HttpDefaults.baseGet("/api/lookup/pupil-by-entry/18535?include=false"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(HttpDefaults.baseGet("/api/lookup/all-entries/false"))
            .pause(GlobalConfig.scenarioPauses) 
            .exec(HttpDefaults.baseGet("/api/lookup/all-sets"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(HttpDefaults.baseGet("/api/lookup/phase/1"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(HttpDefaults.baseGet("/api/lookup/terms"))
            .pause(GlobalConfig.scenarioPauses)
        )
     );

    public static ChainBuilder prepareTest = exec(
        exec(http("Assesment Subsection - Phonemes - Prepare test - Request 1")
            .get("/api/assessment/has-paused-assessment?pupilId=#{pupilId}&phaseId=1&categoryId=1&testFormat=all"))
        .pause(GlobalConfig.scenarioPauses)
        .exec(http("Assesment Subsection - Phonemes - Prepare test - Request 2")
            .get("/api/comments/by-phase?pupilId=#{pupilId}&phaseId=1"))
        .pause(GlobalConfig.scenarioPauses)
        .exec(http("Assesment Subsection - Phonemes - Prepare test - Request 3")
            .get("/api/assessment/has-paused-assessment?pupilId=#{pupilId}&phaseId=1&categoryId=1&testFormat=all"))
        .pause(GlobalConfig.scenarioPauses)        
    );

    public static ChainBuilder beginTest = exec(
        exec(http("Assesment Subsection - Phonemes - Begin test - Request 1")
            .get("/api/assessment/all?phaseId=1&categoryId=1"))
        .pause(GlobalConfig.scenarioPauses)
        .exec(http("Assesment Subsection - Phonemes - Begin test - Request 2")
            .get("/api/comments/by-phase?pupilId=#{pupilId}&phaseId=1"))
        .pause(GlobalConfig.scenarioPauses)
    );

    public static ChainBuilder performTest = exec(
        exec(session ->
                session.set("questionId", "1"))
        .exec(http("Assesment Subsection - Phonemes - Perform test - Request 1")
            .post("/api/assessment/correct")
                .header("content-type", "application/json")
                .body(ElFileBody("user-files\\simulations\\bodies\\Phonemes\\CorrectAnswer.json")))
        .exec(session ->
                { Session nn = session.set("questionId", session.getInt("questionId") + 1);
                return nn; })//Because questionId = 2 does NOT exist
        .pause(GlobalConfig.scenarioPauses)
        .repeat(22)
        .on(
            exec(session ->
                { Session nn = session.set("questionId", session.getInt("questionId") + 1);
                return nn; })
            .exec(http("Assesment Subsection - Phonemes - Perform test - Request 1") //#{questionId}")
                .post("/api/assessment/correct")
                    .header("content-type", "application/json")
                    .body(ElFileBody("user-files\\simulations\\bodies\\Phonemes\\CorrectAnswer.json")))
            .pause(GlobalConfig.scenarioPauses)
        )
    );

    public static ChainBuilder endTest = exec(
        exec(http("Assesment Subsection - Phonemes - End test - Request 1")
            .get("/api/assessment/has-paused-assessment?pupilId=#{pupilId}&phaseId=1&categoryId=1&testFormat=all"))
        .pause(GlobalConfig.scenarioPauses)
    );

    public static ChainBuilder feedPupilIds = exec(
        DbFeeder.feedPupilIds
    );  
}

