package student.avansti.hueapp.parts;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import student.avansti.hueapp.Color;
import student.avansti.hueapp.Utility;
import student.avansti.hueapp.data.DAbstract;
import student.avansti.hueapp.data.DLamp;

public class PartPhilipsHue {

    private static final String LOG_TAG = PartPhilipsHue.class.getSimpleName();

    private static PartPhilipsHue instance;

    private OkHttpClient httpClient;

    private String apiUrl;
    private String username;

    private PartPhilipsHue() {
        this.httpClient = new OkHttpClient();
    }

    public void setBridge(String host, String username) {
        if (Utility.stringIsNullOrWhitespace(host)) throw new IllegalArgumentException("bridgeHost cannot be empty");
        if(Utility.stringIsNullOrWhitespace(username)) throw new IllegalArgumentException("username cannot be empty");

        this.apiUrl = "http://" + host + "/api";
        this.username = username;
    }

    public List<DLamp> getLamps() {
        this.checkIfSetup();

        List<DLamp> result = new ArrayList<>();

        PartUrl partUrl = new PartUrl(this.apiUrl);
        partUrl.addSubDir(username);
        partUrl.addSubDir("lights");

        try (Response response = this.doApiRequest(partUrl)) {
            if(response != null) {
                String stringBody = response.body().string();

                JsonObject responseObj = JsonParser.parseString(stringBody).getAsJsonObject();

                result = responseObj.keySet().stream()
                        .map(e -> {
                            DLamp res = PartGson.getGsonInstance().fromJson(responseObj.get(e), DLamp.class);
                            res.id = e;
                            return res;
                        })
                        .collect(Collectors.toList());
            }
        }
        catch (IOException ex) {
            PartLog.error(PartPhilipsHue.LOG_TAG, ex.getLocalizedMessage());
        }

        return result;
    }

    public DLamp getLampById(String lampId) {
        DLamp result = null;

        Optional<DLamp> optLamp = this.getLamps().stream().filter(e -> e.id.equals(lampId)).findFirst();
        if(optLamp.isPresent()) result = optLamp.get();

        return result;
    }

    public DLamp getLampByUniqueId(String lampUniqueId) {
        DLamp result = null;

        Optional<DLamp> optLamp = this.getLamps().stream().filter(e -> e.uniqueid.equals(lampUniqueId)).findFirst();
        if(optLamp.isPresent()) result = optLamp.get();

        return result;
    }

    public void setLampPowerState(DLamp lamp, boolean onState) {
        if(lamp != null) this.setLampPowerState(lamp.id, onState);
    }

    public void setLampPowerState(String lampId, boolean onState) {
        if(lampId == null) throw new NullPointerException("lampId is null");

        this.checkIfSetup();

        PartUrl partUrl = new PartUrl(this.apiUrl);
        partUrl.addSubDir(this.username);
        partUrl.addSubDir("lights");
        partUrl.addSubDir(lampId);
        partUrl.addSubDir("state");

        DSetPowerStateRequestBody body = new DSetPowerStateRequestBody(onState);

        this.doApiRequest("PUT", partUrl, body, true);
    }

    public void setLampColor(DLamp lamp, Color color) {
        if(lamp != null) this.setLampColor(lamp.id, color);
    }

    public void setLampColor(String lampId, Color color) {
        if(lampId == null) throw new NullPointerException("lampId is null");

        this.checkIfSetup();

        PartUrl partUrl = new PartUrl(this.apiUrl);
        partUrl.addSubDir(this.username);
        partUrl.addSubDir("lights");
        partUrl.addSubDir(lampId);
        partUrl.addSubDir("state");

        DSetColorRequestBody body = new DSetColorRequestBody(color);

        this.doApiRequest("PUT", partUrl, body, true);
    }

    private Response doApiRequest(PartUrl url) {
        return this.doApiRequest("GET", url,null, false);
    }

    private Response doApiRequest(String method, PartUrl url, DAbstract body) {
        return this.doApiRequest(method, url, body, false);
    }

    private Response doApiRequest(String method, PartUrl url, DAbstract body, boolean discardResponse) {
        Response result = null;

        try {
            RequestBody requestBody = null;
            if(body != null) requestBody = RequestBody.create(body.toJson(), MediaType.parse("application/json"));

            Request requestCall = new Request.Builder()
                    .method(method, requestBody)
                    .url(url.result())
                    .build();

            result = this.httpClient.newCall(requestCall).execute();
        }
        catch (IOException ex) {
            PartLog.error(PartPhilipsHue.LOG_TAG, ex.getLocalizedMessage());
            result = null;
        }

        if(discardResponse && result != null) result.close();

        return result;
    }

    private void checkIfSetup() {
        if (Utility.stringIsNullOrWhitespace(this.apiUrl) || Utility.stringIsNullOrWhitespace(this.username))
            throw new UnsupportedOperationException("There is no bridge setup using PartPhilipsHue.setBridge()");
    }

    public static PartPhilipsHue getInstance() {
        if(PartPhilipsHue.instance == null) PartPhilipsHue.instance = new PartPhilipsHue();
        return PartPhilipsHue.instance;
    }

    private static class DSetPowerStateRequestBody extends DAbstract {

        public boolean on;

        public DSetPowerStateRequestBody(boolean on) {
            this.on = on;
        }

    }

    private static class DSetColorRequestBody extends DAbstract {

        public int hue;
        public int sat;
        public int bri;

        public boolean on = true;

        public DSetColorRequestBody(Color clr) {
            this.hue = (int)Math.round(Utility.map(clr.hue(), 0, 360, 0, 65535));
            this.sat = (int)Math.round(Utility.map(clr.saturation(), 0, 100, 0, 254));
            this.bri = (int)Math.round(Utility.map(clr.brightness(), 0, 100, 1, 254));
        }

    }

}
