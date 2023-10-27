import simulations.Pages.Assesment.Phonemes;
import simulations.Pages.Assesment.PhonemesPhase1;
import simulations.Pages.Assesment.HighFrequencyWords;
import simulations.Pages.Assesment.Blending;
import simulations.Pages.Assesment.Segmenting;
import simulations.Pages.Assesment.ScreeningCheck;
import simulations.Pages.Assesment.LetterNames;

import simulations.FrameworkCore.HttpDefaults;

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
    ScenarioBuilder assesment_OpenAllPages = scenario("Assesment - Open all Pages")
        .exec(
            HttpDefaults.feedAccessTokens    
            // ,PhonemesPhase1.openPhonemes_Phase_1
            ,Phonemes.openPhonemes
            // ,HighFrequencyWords.openHighFrequencyWords
            // ,Blending.openBlending
            // ,Segmenting.openSegmenting
            // ,ScreeningCheck.openScreeningCheck
            // ,LetterNames.openLetterNames
        );
    
    ScenarioBuilder assesmentPage_Phonemes_PerformTest = scenario("Assesment Page - Phonemes - Perform Test")
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
            //assesmentPage_Phonemes_PerformTest.injectOpen(
              //  atOnceUsers(1)),
            assesment_OpenAllPages.injectOpen(
                atOnceUsers(1))
                // rampUsers(500).during(60))
        ).protocols(HttpDefaults.httpProtocol);
    }
}
