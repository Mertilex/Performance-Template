package computerdatabase;

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
        .authorizationHeader("Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6ImF0K2p3dCJ9.eyJpc3MiOiJodHRwczovL2xvY2FsaG9zdDo0NDMxNyIsInN1YiI6ImY3NzU0MjQ2LWQ3OTktNGM0Yy05MDcxLWRkMmQxZjg4NWMzMyIsImlkIjoiREYyQTMyNDUzMUU0MDIwQjMxQTA4NDcwNUZCOEVBQTkiLCJzY29wZSI6WyJvcGVuaWQiLCJwcm9maWxlIiwiZW1haWwiXSwiYW1yIjoicHdkIiwiY2xpZW50X2lkIjoiUGhvbmljc1RyYWNrZXJXZWIiLCJhdXRoX3RpbWUiOiIxNjkzNzM3NDQ4IiwiaWRwIjoibG9jYWwiLCJyb2xlIjpbIkdyb3VwQWRtaW4iLCJBZG1pbiJdLCJzY2hvb2xJZCI6IjEiLCJzY2hvb2xOYW1lIjoiUnlkZWxsIEhpZ2giLCJuYW1lIjoiQWRtaW4gTWNDYWZmZXJ5IiwiZW1haWwiOiJtY2NhZmZlcnlwYXVsQGhvdG1haWwuY29tIiwidGl0bGUiOiJNciIsImRhdGFDb25zZW50IjoidHJ1ZSIsImVudHJ5IjoiMSIsInBob25lbWVzUHJvZ3JhbSI6IkxTQiIsInNpZCI6IkZDMTY2ODU1REJBODI2NzRGRTY1RDA0MjE1ODNBNjQxIiwic2V0IjpbIjEwOTA4IiwiMjIzNDIiLCIxMDA1NCIsIjk2MjEiLCIxMDA4MCJdLCJuYmYiOjE2OTM3Mzc0NDgsImV4cCI6MTY5MzczOTI0OH0.SRkcxFZfJtSSJbGMUiQTSjdpg6iPBNGejNWW484hj4cmhxZpzjAADUsVjIw5JB5w-WEDCRxvQCktK_BBitjYYhSIIfkyAOgOh-Ww89ttARpR5_rEbX331XdsbNwZ0Xx8WErwwrRy97_Bx_A0VGgA0ip2Zbz_IOFQphfgNvEuiyf4Kxz3LBTtRjVbGdiNYKNanZ2tKZgBgl6uUHYO_JrpPWATW1Fuy-piGJvZ-tPeHsSDO7fBIqRCFEx4dEI6Ti3zDN4Ci_Wo4iho4ZC4SFTuIZkUwi7E2_cwdEVq2RlAqx-qU0RJaW4rq7Karhc3hvO3yznTC7H1XWDazUzV6oyi0g")
        .acceptEncodingHeader("gzip, deflate")
        .acceptLanguageHeader("en-US,en;q=0.7,pl;q=0.3")
        .upgradeInsecureRequestsHeader("1")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.0");
                
        ChainBuilder openPhonemes_Phase_1 = exec(
            exec(http("Assesment Subsection - Phonemes Phase 1 - Request 1")
                .get("/api/lookup/pupil-by-entry/18535?include=false"))
            .pause(1)
            .exec(http("Assesment Subsection - Phonemes Phase 1 - Request 2")
                .get("/api/lookup/all-entries/false"))
            .pause(1) 
            .exec(http("Assesment Subsection - Phonemes Phase 1 - Request 3")
                .get("/api/lookup/all-sets"))
            .pause(1)
            .exec(http("Assesment Subsection - Phonemes Phase 1 - Request 4")
                .get("/api/lookup/phase/10"))
            .pause(1)
            .exec(http("Assesment Subsection - Phonemes Phase 1 - Request 5")
                .get("/api/lookup/terms"))
            .pause(1)
        );

        ChainBuilder openPhonemes = exec(
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

        ScenarioBuilder firstScenario = scenario("Assesment")
            .exec(openPhonemes_Phase_1
                ,openPhonemes
            );

        {
            setUp(
                firstScenario.injectOpen(atOnceUsers(1))//rampUsers(300).during(30))
            ).protocols(httpProtocol);
        }
}
