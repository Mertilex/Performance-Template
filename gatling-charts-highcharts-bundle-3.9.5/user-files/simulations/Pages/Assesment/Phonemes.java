package simulations.Pages.Assesment;

import simulations.Configs.GlobalConfig;
import simulations.Feeders.DbFeeder;
import static simulations.FrameworkCore.HttpDefaults.baseGet;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class Phonemes {
    public static ChainBuilder openPhonemes = exec(
        group("Phonemes - Open Phonemes section").on(
            exec(baseGet("/api/lookup/pupil-by-entry/18535?include=false"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(baseGet("/api/lookup/all-entries/false"))
            .pause(GlobalConfig.scenarioPauses) 
            .exec(baseGet("/api/lookup/all-sets"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(baseGet("/api/lookup/phase/1"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(baseGet("/api/lookup/terms"))
            .pause(GlobalConfig.scenarioPauses)
        )
     );

    public static ChainBuilder prepareTest = exec(
        group("Phonemes - Prepare Test").on(
            exec(baseGet("/api/assessment/has-paused-assessment?pupilId=#{pupilId}&phaseId=1&categoryId=1&testFormat=all"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(baseGet("/api/comments/by-phase?pupilId=#{pupilId}&phaseId=1"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(baseGet("/api/assessment/has-paused-assessment?pupilId=#{pupilId}&phaseId=1&categoryId=1&testFormat=all"))
            .pause(GlobalConfig.scenarioPauses)
        )
    );

    public static ChainBuilder beginTest = exec(
        group("Phonemes - Begin test").on(
            exec(baseGet("/api/assessment/all?phaseId=1&categoryId=1"))
            .pause(GlobalConfig.scenarioPauses)
            .exec(baseGet("/api/comments/by-phase?pupilId=#{pupilId}&phaseId=1"))
            .pause(GlobalConfig.scenarioPauses)
        )
    );

    public static ChainBuilder performTest = exec(
        group("Phonemes - Perform test").on(
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
        )
    );

    public static ChainBuilder endTest = exec(
        group("Phonemes - End test").on(
            exec(baseGet("/api/assessment/has-paused-assessment?pupilId=#{pupilId}&phaseId=1&categoryId=1&testFormat=all"))
            .pause(GlobalConfig.scenarioPauses)
        )
    );

    public static ChainBuilder feedPupilIds = exec(
        DbFeeder.feedPupilIds
    );  
}

