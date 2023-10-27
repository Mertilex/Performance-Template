package simulations.Feeders;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;

public class AccessTokensFeeder {
    private static FeederBuilder<String> feeder = csv("accessTokens.csv").random(); //TODO: extract name to GlobalConfig file

    public static ChainBuilder feedAccessTokens = exec(
        feed(feeder)
    ); 
}
