package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class DbFeeder extends Simulation {
    private static String dbConnectionString = "jdbc:sqlserver://localhost;Database=PhonicsTrackerAzure_Test;Trusted_Connection=True;MultipleActiveResultSets=true;Encrypt=false";
    private static String dbLogin = "QSuser";
    private static String dbPassword = "QSuser";

    public static ChainBuilder feedPupilIds = exec(
         feed(
            jdbcFeeder(
                dbConnectionString
                ,dbLogin
                ,dbPassword
                ,"SELECT p.Id AS pupilId \r\n" + //
                        "FROM dbo.Pupil AS p\r\n" + //
                        "WHERE p.Active = 1\r\n" + //
                        "AND p.EntryId = 18534").circular().random())
    );



    // private static ChainBuilder feedData(String sql) = exec(
    //     feed(
    //         jdbcFeeder(
    //             dbConnectionString
    //             ,dbLogin
    //             ,dbPassword
    //             ,"SELECT p.Id AS pupilId \r\n" + //
    //                     "FROM dbo.Pupil AS p\r\n" + //
    //                     "WHERE p.Active = 1\r\n" + //
    //                     "AND p.EntryId = 18534").circular().random())
    // );
}