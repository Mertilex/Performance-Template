import simulations.Assesment;
import simulations.PhonemesPhase1;
import simulations.HighFrequencyWords;
import simulations.Phonemes;
import simulations.Blending;
import simulations.Segmenting;
import simulations.ScreeningCheck;

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
    private HttpProtocolBuilder httpProtocol = http
        .baseUrl("https://localhost:44317")
        .inferHtmlResources(
            AllowList(),
            DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
        // .authorizationHeader("Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IjY4Q0I4NjE1N0ZEQUUwMUQ0MEIzNDMwRDRCOTJFNjkxIiwidHlwIjoiYXQrand0In0.eyJpc3MiOiJodHRwczovL2xvY2FsaG9zdDo0NDMxNyIsIm5iZiI6MTY5MzMwOTI2MywiaWF0IjoxNjkzMzA5MjYzLCJleHAiOjE5OTMzMDkyNjMsInNjb3BlIjpbIm9wZW5pZCIsInByb2ZpbGUiLCJlbWFpbCJdLCJhbXIiOlsicHdkIl0sImNsaWVudF9pZCI6IlBob25pY3NUcmFja2VyV2ViIiwic3ViIjoiMDAwNzgyMjYtNjI3NC00YzY1LWJjMTEtNzRkN2U0ZmI0MmM1IiwiYXV0aF90aW1lIjoxNjkzMzA5MjYyLCJpZHAiOiJsb2NhbCIsInJvbGUiOiJUZWFjaGVyIiwic2Nob29sSWQiOiI1Mjg4Iiwic2Nob29sTmFtZSI6IkRlcmVoYW0gQ2h1cmNoIEluZmFudCBhbmQgTnVyc2VyeSBTY2hvb2wiLCJuYW1lIjoiQWxpY2UgV2F0dHMiLCJlbWFpbCI6ImF3YXR0czl3cmhAbnNpeC5vcmcudWsiLCJ0aXRsZSI6Ik1pc3MiLCJkYXRhQ29uc2VudCI6dHJ1ZSwiZW50cnkiOlsiMTg1MzQiLCIxODUzNSJdLCJzZXQiOlsiMTQzNzgiLCI3ODA3IiwiMTQzNzkiLCI3ODA4Il0sInBob25lbWVzUHJvZ3JhbSI6IkdPViIsInNpZCI6IkRFOTUwNkFDNDhFRTI3QkMwNjY2RUQ0M0I4OUM1NDI5IiwianRpIjoiOUI1QzYzMjVBNDIyMjIzQkJDQjU1M0NGRjcxQUFFQzkifQ.qs6sH42hR6EQJrxaqcuLEtq0nJlNI-VqA2lK65FyLFv3dzSp8LwWe9_t1gUgdyrg5-CgYI8omtCzEahb5Zx_DDrtGdxSjmQCg9wzTc4li62kQ-_sxNd5MNftsPUfXXBMo2n-5EA0qAxg_D093IEDKKENiOUal096Y1QjmXf6Z5ZfjrBJCC_leVgGS1MdDv_FE-YoOWPwe-sG-8qU_lGuyg5jmB8Tm8alcp6w3fYAFSdqAZcGVCXB4O9uDSy2NbiF2qJ1Ko9Gbu5ri2eGhqSFhNyT9w6tS8lnBGRgYTX-yebiERPRkhgrAuwJ9WPJnf8dGJFCaO_tEPruYecjDLSFww")
        .authorizationHeader("Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IjY4Q0I4NjE1N0ZEQUUwMUQ0MEIzNDMwRDRCOTJFNjkxIiwidHlwIjoiYXQrand0In0.eyJpc3MiOiJodHRwczovL2xvY2FsaG9zdDo0NDMxNyIsIm5iZiI6MTY5MzMwOTI2MywiaWF0IjoxNjkzMzA5MjYzLCJleHAiOjE5OTMzMDkyNjMsInNjb3BlIjpbIm9wZW5pZCIsInByb2ZpbGUiLCJlbWFpbCJdLCJhbXIiOlsicHdkIl0sImNsaWVudF9pZCI6IlBob25pY3NUcmFja2VyV2ViIiwic3ViIjoiMDAwNzgyMjYtNjI3NC00YzY1LWJjMTEtNzRkN2U0ZmI0MmM1IiwiYXV0aF90aW1lIjoxNjkzMzA5MjYyLCJpZHAiOiJsb2NhbCIsInJvbGUiOiJUZWFjaGVyIiwic2Nob29sSWQiOiI1Mjg4Iiwic2Nob29sTmFtZSI6IkRlcmVoYW0gQ2h1cmNoIEluZmFudCBhbmQgTnVyc2VyeSBTY2hvb2wiLCJuYW1lIjoiQWxpY2UgV2F0dHMiLCJlbWFpbCI6ImF3YXR0czl3cmhAbnNpeC5vcmcudWsiLCJ0aXRsZSI6Ik1pc3MiLCJkYXRhQ29uc2VudCI6dHJ1ZSwiZW50cnkiOlsiMTg1MzQiLCIxODUzNSJdLCJzZXQiOlsiMTQzNzgiLCI3ODA3IiwiMTQzNzkiLCI3ODA4Il0sInBob25lbWVzUHJvZ3JhbSI6IkdPViIsInNpZCI6IkRFOTUwNkFDNDhFRTI3QkMwNjY2RUQ0M0I4OUM1NDI5IiwianRpIjoiOUI1QzYzMjVBNDIyMjIzQkJDQjU1M0NGRjcxQUFFQzkifQ.qs6sH42hR6EQJrxaqcuLEtq0nJlNI-VqA2lK65FyLFv3dzSp8LwWe9_t1gUgdyrg5-CgYI8omtCzEahb5Zx_DDrtGdxSjmQCg9wzTc4li62kQ-_sxNd5MNftsPUfXXBMo2n-5EA0qAxg_D093IEDKKENiOUal096Y1QjmXf6Z5ZfjrBJCC_leVgGS1MdDv_FE-YoOWPwe-sG-8qU_lGuyg5jmB8Tm8alcp6w3fYAFSdqAZcGVCXB4O9uDSy2NbiF2qJ1Ko9Gbu5ri2eGhqSFhNyT9w6tS8lnBGRgYTX-yebiERPRkhgrAuwJ9WPJnf8dGJFCaO_tEPruYecjDLSFww")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("en-US,en;q=0.7,pl;q=0.3")
        .upgradeInsecureRequestsHeader("1")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.0");

        ScenarioBuilder firstScenario = scenario("Assesment")
            .exec(PhonemesPhase1.openPhonemes_Phase_1
                ,Phonemes.openPhonemes
                ,HighFrequencyWords.openHighFrequencyWords
                ,Blending.openBlending
                ,Segmenting.openSegmenting
                ,ScreeningCheck.openScreeningCheck
                ,Assesment.openLetterNames
            );
        
        ScenarioBuilder assesmentPage_Phonemes_Test = scenario("Assesment Page - Phonemes - Test")
            
            .exec(
                Phonemes.feedPupilIds
                ,Phonemes.openPhonemes
                ,Phonemes.prepareTest
                ,Phonemes.beginTest
                ,Phonemes.performTest
                ,Phonemes.endTest
                //
                );

        {
            setUp(
                assesmentPage_Phonemes_Test.injectOpen(
                    //atOnceUsers(300))
                    rampUsers(300).during(60))
            ).protocols(httpProtocol);
        }
}
