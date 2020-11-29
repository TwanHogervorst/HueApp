package student.avansti.hueapp.parts;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import student.avansti.hueapp.Utility;
import student.avansti.hueapp.data.DLamp;

public class PartPhilipsHue {

    private static final String LOG_TAG = PartPhilipsHue.class.getSimpleName();

    private OkHttpClient httpClient;
    private Gson gson;

    private String apiUrl;
    private String username;

    public PartPhilipsHue(String bridgeHost, String username) {
        if (Utility.stringIsNullOrWhitespace(bridgeHost)) throw new IllegalArgumentException("bridgeHost cannot be empty");
        if(Utility.stringIsNullOrWhitespace(username)) throw new IllegalArgumentException("username cannot be empty");

        this.apiUrl = "http://" + bridgeHost + "/api";
        this.username = username;

        this.httpClient = new OkHttpClient();
        this.gson = new Gson();
    }

    public List<DLamp> getLamps() {
        List<DLamp> result = new ArrayList<>();

        PartUrl partUrl = new PartUrl(this.apiUrl);
        partUrl.addSubDir(username);
        partUrl.addSubDir("lights");

        Request requestCall = new Request.Builder()
                .url(partUrl.result())
                .build();

        try (Response response = this.httpClient.newCall(requestCall).execute()) {
            String stringBody = response.body().string();

            JsonObject responseObj = JsonParser.parseString(stringBody).getAsJsonObject();

            result = responseObj.keySet().stream()
                    .map(e -> this.gson.fromJson(responseObj.get(e), DLamp.class))
                    .collect(Collectors.toList());
        }
        catch (IOException ex) {
            Log.e(PartPhilipsHue.LOG_TAG, ex.getLocalizedMessage());
        }

        return result;
    }

}
