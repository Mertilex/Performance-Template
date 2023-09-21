import simulations.LetterNames;
import simulations.PhonemesPhase1;
import simulations.HighFrequencyWords;
import simulations.Phonemes;
import simulations.Blending;
import simulations.Segmenting;
import simulations.ScreeningCheck;
import simulations.HttpDefaults;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class PhonicsTracker extends Simulation
{
    

         

        ScenarioBuilder firstScenario = scenario("Assesment")
            .exec(PhonemesPhase1.openPhonemes_Phase_1
                ,Phonemes.openPhonemes
                ,HighFrequencyWords.openHighFrequencyWords
                ,Blending.openBlending
                ,Segmenting.openSegmenting
                ,ScreeningCheck.openScreeningCheck
                ,LetterNames.openLetterNames
            );
        
        ScenarioBuilder assesmentPage_Phonemes_Test = scenario("Assesment Page - Phonemes - Test")
            .exec(
                HttpDefaults.feedAccessTokens
                ,Phonemes.feedPupilIds
                ,Phonemes.openPhonemes
                ,Phonemes.prepareTest
                ,Phonemes.beginTest
                ,Phonemes.performTest
                ,Phonemes.endTest
                );

        {
            setUp(
                assesmentPage_Phonemes_Test.injectOpen(
                    // atOnceUsers(10))
                    rampUsers(500).during(60))
            ).protocols(HttpDefaults.httpProtocol);
        }
}
