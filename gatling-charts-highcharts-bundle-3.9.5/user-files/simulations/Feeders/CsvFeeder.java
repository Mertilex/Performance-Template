package simulations.Feeders;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;

public class CsvFeeder {
    private static FeederBuilder<String> feeder = csv("search.csv").random(); //TODO: extract name to GlobalConfig file

    public static ChainBuilder feedSearchCriteria = exec(
        feed(feeder)
    );
}