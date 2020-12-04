package student.avansti.hueapp.parts;

import android.provider.Telephony;

import junit.framework.TestCase;

import java.util.List;

import student.avansti.hueapp.Color;
import student.avansti.hueapp.data.DLamp;
import student.avansti.hueapp.mocks.PartLogMock;

public class PartPhilipsHueTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        PartPhilipsHue.getInstance().setBridge("127.0.0.1:80","newdeveloper");
    }

    public void testSetBridge() {

        PartPhilipsHue partPhilipsHue = PartPhilipsHue.getInstance();
        List<DLamp> lampList = partPhilipsHue.getLamps();

        // current bridge (127.0.0.1:80) works
        assertTrue(lampList.size() > 0);

        // "localhost" also works
        partPhilipsHue.setBridge("localhost", "newdeveloper");

        lampList = partPhilipsHue.getLamps();
        assertTrue(lampList.size() > 0);

        // We know this part will log errors, so we mock the log dependecy
        PartLog.setInstance(new PartLogMock());

        // Wrong ip fails
        partPhilipsHue.setBridge("192.168.1.1", "newdeveloper");

        lampList = partPhilipsHue.getLamps();
        assertFalse(lampList.size() > 0);

        // wrong (but real) host fails
        partPhilipsHue.setBridge("example.com", "newdeveloper");

        lampList = partPhilipsHue.getLamps();
        assertFalse(lampList.size() > 0);

        // wrong username fails
        partPhilipsHue.setBridge("127.0.0.1:80", "unittestwrong");

        lampList = partPhilipsHue.getLamps();
        assertFalse(lampList.size() > 0);

        // return to normal log behaviour
        PartLog.setInstance(null);
    }

    public void testGetLights() {

        PartPhilipsHue partPhilipsHue = PartPhilipsHue.getInstance();
        List<DLamp> lampList = partPhilipsHue.getLamps();

        assertTrue(lampList.size() > 0);

    }

    public void testGetLampById() {

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

        PartPhilipsHue partPhilipsHue = PartPhilipsHue.getInstance();
        List<DLamp> lampList = partPhilipsHue.getLamps();

        assertTrue(lampList.size() > 0);

        DLamp lampById = partPhilipsHue.getLampByUniqueId(lampList.get(0).uniqueid);

        assertNotNull(lampById);
        assertNotNull(lampById.id);
        assertNotNull(lampById.uniqueid);
        assertNotNull(lampById.state);

    }

    public void testSetLightPowerState() {

        PartPhilipsHue partPhilipsHue = PartPhilipsHue.getInstance();
        List<DLamp> lampList = partPhilipsHue.getLamps();

        assertTrue(lampList.size() > 0);

        DLamp lampToTest = lampList.get(0);

        assertNotNull(lampToTest);
        assertNotNull(lampToTest.state);

        boolean currentState = lampToTest.state.on;

        partPhilipsHue.setLampPowerState(lampToTest, !currentState);
        lampToTest = partPhilipsHue.getLampById(lampToTest.id);

        assertEquals(!currentState, lampToTest.state.on);

        // restore powerstate
        partPhilipsHue.setLampPowerState(lampToTest, currentState);
        lampToTest = partPhilipsHue.getLampById(lampToTest.id);

        assertEquals(currentState, lampToTest.state.on);

    }

    public void testSetLightColor() {

        PartPhilipsHue partPhilipsHue = PartPhilipsHue.getInstance();
        List<DLamp> lampList = partPhilipsHue.getLamps();

        assertTrue(lampList.size() > 0);

        DLamp lampToTest = lampList.get(0);

        assertNotNull(lampToTest);
        assertNotNull(lampToTest.state);

        Color redColor = new Color(255, 0, 0);
        Color greenColor = new Color(0, 255, 0);
        Color blueColor = new Color(0, 0, 255);

        // test red
        partPhilipsHue.setLampColor(lampToTest, redColor);
        lampToTest = partPhilipsHue.getLampById(lampToTest.id);

        assertNotNull(lampToTest);
        assertNotNull(lampToTest.state);

        assertEquals(redColor.red(), lampToTest.state.getColor().red());

        // test green
        partPhilipsHue.setLampColor(lampToTest, greenColor);
        lampToTest = partPhilipsHue.getLampById(lampToTest.id);

        assertNotNull(lampToTest);
        assertNotNull(lampToTest.state);

        assertEquals(greenColor.green(), lampToTest.state.getColor().green());

        // test blue
        partPhilipsHue.setLampColor(lampToTest, blueColor);
        lampToTest = partPhilipsHue.getLampById(lampToTest.id);

        assertNotNull(lampToTest);
        assertNotNull(lampToTest.state);

        assertEquals(blueColor.blue(), lampToTest.state.getColor().blue());
    }
}