package simulations.Configs;

public class GlobalConfig {
    //General
    public static final int scenarioPauses = 1; //In seconds

    //Application
    public static final String appUrl = "https://localhost:44317";

    //Database
    public static final String dbConnectionString = "jdbc:sqlserver://localhost;Database=PhonicsTrackerAzure_Test;Trusted_Connection=True;MultipleActiveResultSets=true;Encrypt=false";
    public static final String dbLogin = "QSuser";
    public static final String dbPassword = "QSuser";
}
