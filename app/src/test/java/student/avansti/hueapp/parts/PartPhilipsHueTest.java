package student.avansti.hueapp.parts;

import android.provider.Telephony;

import junit.framework.TestCase;

import java.util.List;

import student.avansti.hueapp.Color;
import student.avansti.hueapp.data.DLamp;

public class PartPhilipsHueTest extends TestCase {

    private final String BRIDGE_HOST = "192.168.1.43:80";
    private final String USERNAME = "newdeveloper";

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        PartPhilipsHue.getInstance().setBridge("192.168.1.43:80","newdeveloper");
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