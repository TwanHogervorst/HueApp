package student.avansti.hueapp.parts;

import android.provider.Telephony;

import junit.framework.TestCase;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import student.avansti.hueapp.Color;
import student.avansti.hueapp.data.DLamp;

public class PartPhilipsHueMockedTest extends TestCase {

    private final MockWebServer mockWebServer = new MockWebServer();

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        this.mockWebServer.start();
        PartPhilipsHue.getInstance().setBridge(this.getMockHost(), "newdeveloper");
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();

        this.mockWebServer.shutdown();
    }

    public void testGetLights() {

        this.enqueueDefaultResponse();

        PartPhilipsHue partPhilipsHue = PartPhilipsHue.getInstance();
        List<DLamp> lampList = partPhilipsHue.getLamps();

        assertTrue(lampList.size() > 0);

    }

    public void testGetLampById() {

        this.enqueueDefaultResponse();
        this.enqueueDefaultResponse();

        PartPhilipsHue partPhilipsHue = PartPhilipsHue.getInstance();
        List<DLamp> lampList = partPhilipsHue.getLamps();

        assertTrue(lampList.size() > 0);

        DLamp lampById = partPhilipsHue.getLampById(lampList.get(0).id);

        assertNotNull(lampById);
        assertNotNull(lampById.id);
        assertNotNull(lampById.uniqueid);
        assertNotNull(lampById.state);

    }

    public void testGetLampByUniqueId() {

        this.enqueueDefaultResponse();
        this.enqueueDefaultResponse();

        PartPhilipsHue partPhilipsHue = PartPhilipsHue.getInstance();
        List<DLamp> lampList = partPhilipsHue.getLamps();

        assertTrue(lampList.size() > 0);

        DLamp lampById = partPhilipsHue.getLampByUniqueId(lampList.get(0).uniqueid);

        assertNotNull(lampById);
        assertNotNull(lampById.id);
        assertNotNull(lampById.uniqueid);
        assertNotNull(lampById.state);

    }

    public void testSetLightPowerState() throws InterruptedException {

        this.enqueueDefaultResponse();
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        PartPhilipsHue partPhilipsHue = PartPhilipsHue.getInstance();
        List<DLamp> lampList = partPhilipsHue.getLamps();
        this.mockWebServer.takeRequest(100, TimeUnit.MILLISECONDS);

        assertTrue(lampList.size() > 0);

        DLamp lampToTest = lampList.get(0);

        assertNotNull(lampToTest);
        assertNotNull(lampToTest.state);

        boolean currentState = lampToTest.state.on;

        partPhilipsHue.setLampPowerState(lampToTest, !currentState);
        RecordedRequest toggleRequest = this.mockWebServer.takeRequest(100, TimeUnit.MILLISECONDS);

        assertNotNull(toggleRequest);
        assertEquals("PUT", toggleRequest.getMethod());
        assertEquals("/api/newdeveloper/lights/1/state", toggleRequest.getPath());
        assertEquals("{\"on\":false}", toggleRequest.getBody().readUtf8());
    }

    public void testSetLightColor() throws InterruptedException {

        this.enqueueDefaultResponse();
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200));

        PartPhilipsHue partPhilipsHue = PartPhilipsHue.getInstance();
        List<DLamp> lampList = partPhilipsHue.getLamps();
        this.mockWebServer.takeRequest(100, TimeUnit.MILLISECONDS);

        assertTrue(lampList.size() > 0);

        DLamp lampToTest = lampList.get(0);

        assertNotNull(lampToTest);
        assertNotNull(lampToTest.state);

        Color color = new Color(255, 255, 255);

        partPhilipsHue.setLampColor(lampToTest, color);
        RecordedRequest toggleRequest = this.mockWebServer.takeRequest(100, TimeUnit.MILLISECONDS);

        assertNotNull(toggleRequest);
        assertEquals("PUT", toggleRequest.getMethod());
        assertEquals("/api/newdeveloper/lights/1/state", toggleRequest.getPath());
        assertEquals("{\"hue\":0,\"sat\":0,\"bri\":254,\"on\":true}", toggleRequest.getBody().readUtf8());
    }

    private void enqueueDefaultResponse() {

        this.mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{" +
                "\"1\": {\n" +
                "        \"modelid\": \"LCT001\",\n" +
                "        \"name\": \"Hue Lamp 1\",\n" +
                "        \"swversion\": \"65003148\",\n" +
                "        \"state\": {\n" +
                "            \"xy\": [\n" +
                "                0,\n" +
                "                0\n" +
                "            ],\n" +
                "            \"ct\": 0,\n" +
                "            \"alert\": \"none\",\n" +
                "            \"sat\": 254,\n" +
                "            \"effect\": \"none\",\n" +
                "            \"bri\": 254,\n" +
                "            \"hue\": 4444,\n" +
                "            \"colormode\": \"hs\",\n" +
                "            \"reachable\": true,\n" +
                "            \"on\": true\n" +
                "        },\n" +
                "        \"type\": \"Extended color light\",\n" +
                "        \"pointsymbol\": {\n" +
                "            \"1\": \"none\",\n" +
                "            \"2\": \"none\",\n" +
                "            \"3\": \"none\",\n" +
                "            \"4\": \"none\",\n" +
                "            \"5\": \"none\",\n" +
                "            \"6\": \"none\",\n" +
                "            \"7\": \"none\",\n" +
                "            \"8\": \"none\"\n" +
                "        },\n" +
                "        \"uniqueid\": \"00:17:88:01:00:d4:12:08-0a\"\n" +
                "    },\n" +
                "    \"2\": {\n" +
                "        \"modelid\": \"LCT001\",\n" +
                "        \"name\": \"Hue Lamp 2\",\n" +
                "        \"swversion\": \"65003148\",\n" +
                "        \"state\": {\n" +
                "            \"xy\": [\n" +
                "                0.346,\n" +
                "                0.3568\n" +
                "            ],\n" +
                "            \"ct\": 201,\n" +
                "            \"alert\": \"none\",\n" +
                "            \"sat\": 144,\n" +
                "            \"effect\": \"none\",\n" +
                "            \"bri\": 254,\n" +
                "            \"hue\": 23536,\n" +
                "            \"colormode\": \"hs\",\n" +
                "            \"reachable\": true,\n" +
                "            \"on\": true\n" +
                "        },\n" +
                "        \"type\": \"Extended color light\",\n" +
                "        \"pointsymbol\": {\n" +
                "            \"1\": \"none\",\n" +
                "            \"2\": \"none\",\n" +
                "            \"3\": \"none\",\n" +
                "            \"4\": \"none\",\n" +
                "            \"5\": \"none\",\n" +
                "            \"6\": \"none\",\n" +
                "            \"7\": \"none\",\n" +
                "            \"8\": \"none\"\n" +
                "        },\n" +
                "        \"uniqueid\": \"00:17:88:01:00:d4:12:08-0b\"\n" +
                "    },\n" +
                "    \"3\": {\n" +
                "        \"modelid\": \"LCT001\",\n" +
                "        \"name\": \"Hue Lamp 3\",\n" +
                "        \"swversion\": \"65003148\",\n" +
                "        \"state\": {\n" +
                "            \"xy\": [\n" +
                "                0.346,\n" +
                "                0.3568\n" +
                "            ],\n" +
                "            \"ct\": 201,\n" +
                "            \"alert\": \"none\",\n" +
                "            \"sat\": 254,\n" +
                "            \"effect\": \"none\",\n" +
                "            \"bri\": 254,\n" +
                "            \"hue\": 65136,\n" +
                "            \"colormode\": \"hs\",\n" +
                "            \"reachable\": true,\n" +
                "            \"on\": true\n" +
                "        },\n" +
                "        \"type\": \"Extended color light\",\n" +
                "        \"pointsymbol\": {\n" +
                "            \"1\": \"none\",\n" +
                "            \"2\": \"none\",\n" +
                "            \"3\": \"none\",\n" +
                "            \"4\": \"none\",\n" +
                "            \"5\": \"none\",\n" +
                "            \"6\": \"none\",\n" +
                "            \"7\": \"none\",\n" +
                "            \"8\": \"none\"\n" +
                "        },\n" +
                "        \"uniqueid\": \"00:17:88:01:00:d4:12:08-0c\"\n" +
                "    }}"));

    }

    private String getMockHost() {
        return this.mockWebServer.getHostName() + ":" + this.mockWebServer.getPort();
    }

}
