package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class MyStepdefs {

    Station station1 = new Station();
    Station station2 = new Station();
    JSONObject jsonObject1;
    JSONObject jsonObject2;
    String currentURL;
    HashMap<String, Object> currentHashMap;
    HashMap<String, Object> station1HashMap;
    HashMap<String, Object> station2HashMap;
    String station1URL;
    String station2URL;

    @Given("The post request does not have an API key")
    public void thePostRequestDoesNotHaveAnAPIKey() {
        currentURL = "http://api.openweathermap.org/data/3.0/stations";
    }

    @When("The post request is made")
    public void thePostRequestIsMade() throws IOException {
        apiFunctions apiFunctions = new apiFunctions();
        String body = "";
        currentHashMap = apiFunctions.postRequest(currentURL, body);
    }

    @Then("The HTTP response code should be {int}")
    public void theHTTPResponseCodeShouldBe(int arg0) {
        int code = (int) currentHashMap.get("code");
        assertEquals(arg0, code);
    }

    @And("The response message text should read correctly")
    public void theResponseMessageTextShouldReadCorrectly() {
        String expectedMessage = "Invalid API key. Please see http://openweathermap.org/faq#error401 for more info.";
        String actualMessage = (String) currentHashMap.get("message");
        assertEquals(expectedMessage, actualMessage);
    }

    @Given("Details for two stations")
    public void detailsForTwoStations() {
        station1.createStation("DEMO_TEST001", "Interview Station 1", 33.33, -111.43, 444);
        station2.createStation("Interview1", "Interview Station 2", 33.44, -12.44, 444);
    }

    @When("The post request for the two stations is made")
    public void thePostRequestForTheTwoStationsIsMade() throws IOException {
        apiFunctions apiFunctions = new apiFunctions();
        currentURL = "http://api.openweathermap.org/data/3.0/stations?appid=95f11c31e481935a56d1c3d67a6c1419";
        station1HashMap = apiFunctions.postRequest(currentURL, station1.toString());
        station2HashMap = apiFunctions.postRequest(currentURL, station2.toString());
    }

    @Then("The HTTP response code for the two stations should be {int}")
    public void theHTTPResponseCodeForTheTwoStationsShouldBe(int arg0) {
        int code = (int) station1HashMap.get("code");
        assertEquals(arg0, code);
        code = (int) station2HashMap.get("code");
        assertEquals(arg0, code);
    }

    @Given("Store attempts were made for two stations")
    public void storeAttemptsWereMadeForTwoStations() throws IOException {
        detailsForTwoStations();
        thePostRequestForTheTwoStationsIsMade();
    }

    @When("A get request for the two stations is made")
    public void aGetRequestForTheTwoStationsIsMade() throws IOException {
        apiFunctions apiFunctions = new apiFunctions();
        Response response = (Response) station1HashMap.get("response");
        jsonObject1 = new JSONObject(Objects.requireNonNull(response.body()).string());
        String stationID = jsonObject1.getString("ID");
        station1URL = "http://api.openweathermap.org/data/3.0/stations/" + stationID + "?appid=95f11c31e481935a56d1c3d67a6c1419";
        station1HashMap = apiFunctions.getRequest(station1URL);
        response = (Response) station2HashMap.get("response");
        jsonObject2 = new JSONObject(Objects.requireNonNull(response.body()).string());
        stationID = jsonObject2.getString("ID");
        station2URL = "http://api.openweathermap.org/data/3.0/stations/" + stationID + "?appid=95f11c31e481935a56d1c3d67a6c1419";
        station2HashMap = apiFunctions.getRequest(station2URL);
    }

    @Then("The two stations should be found in the API database")
    public void theTwoStationsShouldBeFoundInTheAPIDatabase() {
        int code = (int) station1HashMap.get("code");
        assertEquals(200, code);
        code = (int) station2HashMap.get("code");
        assertEquals(200, code);
    }

    @And("The stations values are the same as their post request")
    public void theStationsValuesAreTheSameAsTheirPostRequest() {
        assertEquals(jsonObject1.getString("external_id"), station1.getExternal_id());
        assertEquals(jsonObject1.getString("name"), station1.getName());
        assertEquals(jsonObject1.getDouble("longitude"), station1.getLongitude());
        assertEquals(jsonObject1.getDouble("latitude"), station1.getLatitude());
        assertEquals(jsonObject1.getInt("altitude"), station1.getAltitude());
        assertEquals(jsonObject2.getString("external_id"), station2.getExternal_id());
        assertEquals(jsonObject2.getString("name"), station2.getName());
        assertEquals(jsonObject2.getDouble("longitude"), station2.getLongitude());
        assertEquals(jsonObject2.getDouble("latitude"), station2.getLatitude());
        assertEquals(jsonObject2.getInt("altitude"), station2.getAltitude());
    }

    @Given("Two stations have been stored")
    public void twoStationsHaveBeenStored() throws IOException {
        detailsForTwoStations();
        thePostRequestForTheTwoStationsIsMade();
        aGetRequestForTheTwoStationsIsMade();
        theTwoStationsShouldBeFoundInTheAPIDatabase();
        theStationsValuesAreTheSameAsTheirPostRequest();
    }

    @When("A delete request for the two stations is made")
    public void aDeleteRequestForTheTwoStationsIsMade() throws IOException {
        apiFunctions apiFunctions = new apiFunctions();
        station1HashMap = apiFunctions.deleteRequest(station1URL);
        station2HashMap = apiFunctions.deleteRequest(station2URL);
    }

    @Then("The HTTP response should be {int}")
    public void theHTTPResponseShouldBe(int arg0) {
        int code = (int) station1HashMap.get("code");
        assertEquals(arg0, code);
        code = (int) station2HashMap.get("code");
        assertEquals(arg0, code);
    }

    @Given("A delete request for two stations has been made")
    public void aDeleteRequestForTwoStationsHasBeenMade() throws IOException {
        twoStationsHaveBeenStored();
        aDeleteRequestForTheTwoStationsIsMade();
    }

    @When("Another delete request is made for the two stations")
    public void anotherDeleteRequestIsMadeForTheTwoStations() throws IOException {
        aDeleteRequestForTheTwoStationsIsMade();
    }

    @Then("the new HTTP response should be {int}")
    public void theNewHTTPResponseShouldBe(int arg0) {
        int code = (int) station1HashMap.get("code");
        assertEquals(arg0, code);
        code = (int) station2HashMap.get("code");
        assertEquals(arg0, code);
    }
}
