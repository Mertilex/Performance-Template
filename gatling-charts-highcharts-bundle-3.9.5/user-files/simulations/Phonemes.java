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

    public static ChainBuilder prepareTest = exec(
        exec(http("Assesment Subsection - Phonemes - Prepare test - Request 1")
            .get("/api/assessment/has-paused-assessment?pupilId=${pupilId}&phaseId=1&categoryId=1&testFormat=all"))
        .pause(1)
        // .exec(http("Assesment Subsection - Phonemes - Prepare test - Request 1")
        //     .get(""))
        // .pause(1)
        // .exec(http("Assesment Subsection - Phonemes - Prepare test - Request 1")
        //     .get(""))
        // .pause(1)
        // .exec(http("Assesment Subsection - Phonemes - Prepare test - Request 1")
        //     .get(""))
        // .pause(1)
        // .exec(http("Assesment Subsection - Phonemes - Prepare test - Request 1")
        //     .get(""))
        // .pause(1)
        // .exec(http("Assesment Subsection - Phonemes - Prepare test - Request 1")
        //     .get(""))
        // .pause(1)
        
    );

    public static ChainBuilder feedPupilIds = exec(
        feed(
            jdbcFeeder(
                "jdbc:sqlserver://localhost;Database=PhonicsTrackerAzure_Test;Trusted_Connection=True;MultipleActiveResultSets=true;Encrypt=false"
                ,"QSuser"
                ,"QSuser"
                ,"SELECT p.Id AS pupilId \r\n" + //
                        "FROM dbo.Pupil AS p\r\n" + //
                        "WHERE p.Active = 1\r\n" + //
                        "AND p.EntryId = 18534").circular().random())
    );  
}

