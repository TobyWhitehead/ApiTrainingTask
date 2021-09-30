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
    }

    @When("The post request for the two stations is made")
    public void thePostRequestForTheTwoStationsIsMade() {
    }

    @Then("The HTTP response code for the two stations should be")
    public void theHTTPResponseCodeForTheTwoStationsShouldBe() {
    }
}
