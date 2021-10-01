package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MyStepdefs {

    String code;
    String message;
    Response response1;
    Response response2;
    Station station1 = new Station();
    Station station2 = new Station();
    Response response3;
    Response response4;
    JSONObject jsonObject1;
    JSONObject jsonObject2;
    Response response5;
    Response response6;

    @Given("The post request does not have an API key")
    public void thePostRequestDoesNotHaveAnAPIKey() {
    }

    @When("The post request is made")
    public void thePostRequestIsMade() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/3.0/stations")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.message();
        JSONObject jsonObject = new JSONObject(jsonString);
        code = jsonObject.getString("cod");
        message = jsonObject.getString("message");
    }

    @Then("The HTTP response code should be {int}")
    public void theHTTPResponseCodeShouldBe(int arg0) {
        assertEquals(arg0, Integer.parseInt(code));
    }

    @And("The response message text should read correctly")
    public void theResponseMessageTextShouldReadCorrectly() {
        String expectedMessage = "Invalid API key. Please see http://openweathermap.org/faq#error401 for more info.";
        assertEquals(expectedMessage, message);
    }

    @Given("Details for two stations")
    public void detailsForTwoStations() {
        station1.createStation("DEMO_TEST001", "Interview Station 1", 33.33, -111.43, 444);
        station2.createStation("Interview1", "Interview Station 2", 33.44, -12.44, 444);
    }

    @When("The post request for the two stations is made")
    public void thePostRequestForTheTwoStationsIsMade() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n\"external_id\": \""+station1.getExternal_id()+"\",\r\n\"name\": \""+station1.getName()+"\",\r\n\"latitude\": "+station1.getLatitude()+",\r\n\"longitude\": "+station1.getLongitude()+",\r\n\"altitude\": "+station1.getAltitude()+"\r\n}");
        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/3.0/stations?appid=95f11c31e481935a56d1c3d67a6c1419")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        response1 = client.newCall(request).execute();
        OkHttpClient client2 = new OkHttpClient().newBuilder()
                .build();
        RequestBody body2 = RequestBody.create(mediaType, "{\r\n\"external_id\": \""+station2.getExternal_id()+"\",\r\n\"name\": \""+station2.getName()+"\",\r\n\"latitude\": "+station2.getLatitude()+",\r\n\"longitude\": "+station2.getLongitude()+",\r\n\"altitude\": "+station2.getAltitude()+"\r\n}");
        Request request2 = new Request.Builder()
                .url("http://api.openweathermap.org/data/3.0/stations?appid=95f11c31e481935a56d1c3d67a6c1419")
                .method("POST", body2)
                .addHeader("Content-Type", "application/json")
                .build();
        response2 = client2.newCall(request2).execute();
    }

    @Then("The HTTP response code for the two stations should be {int}")
    public void theHTTPResponseCodeForTheTwoStationsShouldBe(int arg0) {
        assertEquals(arg0, response1.code());
        assertEquals(arg0, response2.code());
    }

    @Given("Store attempts were made for two stations")
    public void storeAttemptsWereMadeForTwoStations() throws IOException {
        detailsForTwoStations();
        thePostRequestForTheTwoStationsIsMade();
    }

    @When("A get request for the two stations is made")
    public void aGetRequestForTheTwoStationsIsMade() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        String jsonString1 = response1.body().string();
        jsonObject1 = new JSONObject(jsonString1);
        String station1ID = jsonObject1.getString("ID");
        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/3.0/stations/"+station1ID+"?appid=95f11c31e481935a56d1c3d67a6c1419")
                .method("GET", null)
                .build();
        response3 = client.newCall(request).execute();
        OkHttpClient client2 = new OkHttpClient().newBuilder()
                .build();
        String jsonString2 = response2.body().string();
        jsonObject2 = new JSONObject(jsonString2);
        String station2ID = jsonObject2.getString("ID");
        Request request2 = new Request.Builder()
                .url("http://api.openweathermap.org/data/3.0/stations/"+station2ID+"?appid=95f11c31e481935a56d1c3d67a6c1419")
                .method("GET", null)
                .build();
        response4 = client2.newCall(request2).execute();
    }

    @Then("The two stations should be found in the API database")
    public void theTwoStationsShouldBeFoundInTheAPIDatabase() {
        assertEquals(200, response3.code());
        assertEquals(200, response4.code());
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
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        String station1ID = jsonObject1.getString("ID");
        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/3.0/stations/"+station1ID+"?appid=95f11c31e481935a56d1c3d67a6c1419")
                .method("DELETE", null)
                .build();
        response5 = client.newCall(request).execute();
        OkHttpClient client2 = new OkHttpClient().newBuilder()
                .build();
        String station2ID = jsonObject2.getString("ID");
        Request request2 = new Request.Builder()
                .url("http://api.openweathermap.org/data/3.0/stations/"+station2ID+"?appid=95f11c31e481935a56d1c3d67a6c1419")
                .method("DELETE", null)
                .build();
        response6 = client2.newCall(request2).execute();
    }

    @Then("The HTTP response should be {int}")
    public void theHTTPResponseShouldBe(int arg0) {
        assertEquals(arg0, response5.code());
        assertEquals(arg0, response6.code());
    }
}
