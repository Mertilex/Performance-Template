package simulations.Configs;

public class GlobalConfig {
    //General
    public static final int scenarioPauses = 1; //In seconds

    //Application
    public static final String appUrl = "http://computer-database.gatling.io"; //EXAMPLE: http://computer-database.gatling.io

    //Database
    public static final String dbConnectionString = "SET_CONNECTION_STRING_HERE"; //EXAMPLE: jdbc:sqlserver://localhost;Database=PhonicsTrackerAzure_Test;Trusted_Connection=True;MultipleActiveResultSets=true;Encrypt=false
    public static final String dbLogin = "SET_DBUSER_HERE";
    public static final String dbPassword = "SET_DBUSER_PASS_HERE";
}
