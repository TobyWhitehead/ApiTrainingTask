package com.ApiTraining;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n\"external_id\": \"DEMO_TEST001\",\r\n\"name\": \"Interview Station 22\",\r\n\"latitude\": 33.33,\r\n\"longitude\": -111.43,\r\n\"altitude\": 444\r\n}");
        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/3.0/stations?appid=95f11c31e481935a56d1c3d67a6c1419")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
    }
}
