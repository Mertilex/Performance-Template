package simulations.Feeders;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;
import simulations.Configs.GlobalConfig;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class DbFeeder {
    private static String dbConnectionString = GlobalConfig.dbConnectionString;
    private static String dbLogin = GlobalConfig.dbLogin;
    private static String dbPassword = GlobalConfig.dbPassword;

    private static FeederBuilder fetchData(String sql) {
        return jdbcFeeder(
                dbConnectionString
                ,dbLogin
                ,dbPassword
                ,sql).circular().random();
    }
    
    public static ChainBuilder feedPupilIds = exec(
        feed(
            fetchData("SELECT p.Id AS pupilId \r\n" + //
                    "FROM dbo.Pupil AS p\r\n" + //
                    "WHERE p.Active = 1\r\n" + //
                    "AND p.EntryId = 18534"))
    );
}