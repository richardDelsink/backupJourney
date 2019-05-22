package restTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Journey;
import domain.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.JourneyJson;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.junit.Assert.*;

public class JourneyRestAssureTest {

    @Before
    public void setUp() throws Exception {
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/TripJourney/api/";
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getJourney() {
        given().
                contentType("application/json").
                pathParam("journeyname", "Canada").
                when().
                get("/Journey/get/{journeyname}").
                then().
                statusCode(500);
    }

    @Test
    public void createJourney() throws IOException {
        int noOfDays = 15;

        Calendar cal = Calendar.getInstance();
        Date ss = cal.getTime();

        cal.add(Calendar.DAY_OF_YEAR, noOfDays);
        Date date = cal.getTime();


        //JourneyJson jj = new JourneyJson("Canada", "Going to Canada all alone","friends",1);


        Journey journey = new Journey("Canada", "Going to Canada all alone",ss,date,"friends","Willem");

                given().
                    contentType("application/json").
                    body(journey).
                    log().all().
                when().
                    post("/Journey").getBody().as(Journey.class);
        //ResponseBody body = response.getBody();
                //statusCode(204);
    }

    @Test
    public void getJourneyByUser() {
        given().
                contentType("application/json").
                pathParam("name", "Willem").
                when().
                get("/Journey/search/{name}").
                then().log().all().
                statusCode(200);
    }

    @Test
    public void deleteJourney() {
        given().
                pathParam("name", "Canada").
                when().
                delete("/Journey/delete/{name}").
                then().log().all().
                statusCode(500);
    }
}
