package stepDefinitions;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class apiFunctions {


    public HashMap<String, Object> postRequest(String inputURL, String inputBody) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(inputBody, mediaType);
        Request request = new Request.Builder()
                .url(inputURL)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = Objects.requireNonNull(response.body()).string();
        System.out.println(jsonString);
        JSONObject jsonObject = new JSONObject(jsonString);
        HashMap<String, Object> outputMap = new HashMap<>();
        outputMap.put("response", response);
        try {
            int code = jsonObject.getInt("code");
            String message = jsonObject.getString("message");
            outputMap.put("code", code);
            outputMap.put("message", message);
        } catch(JSONException e) {
            int code = response.code();
            String message = response.message();
            outputMap.put("code", code);
            outputMap.put("message", message);
        }
        return outputMap;
    }

    public HashMap<String, Object> getRequest(String inputURL) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(inputURL)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = Objects.requireNonNull(response.body()).string();
        JSONObject jsonObject = new JSONObject(jsonString);
        HashMap<String, Object> outputMap = new HashMap<>();
        outputMap.put("response", response);
        try {
            int code = jsonObject.getInt("code");
            String message = jsonObject.getString("message");
            outputMap.put("code", code);
            outputMap.put("message", message);
        } catch(JSONException e) {
            int code = response.code();
            String message = response.message();
            outputMap.put("code", code);
            outputMap.put("message", message);
        }
        return outputMap;
    }

    public HashMap<String, Object> deleteRequest(String inputURL) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(inputURL)
                .method("DELETE", null)
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = Objects.requireNonNull(response.body()).string();
        JSONObject jsonObject = new JSONObject(jsonString);
        int code = jsonObject.getInt("code");
        String message = jsonObject.getString("message");
        HashMap<String, Object> outputMap = new HashMap<>();
        outputMap.put("response", response);
        outputMap.put("code", code);
        outputMap.put("message", message);
        return outputMap;
    }
}
