package student.avansti.hueapp.parts;

import junit.framework.TestCase;

import java.util.List;

import student.avansti.hueapp.Utility;
import student.avansti.hueapp.data.DLamp;

public class PartPhilipsHueTest extends TestCase {

    public void testGetLights() {

        PartPhilipsHue partPhilipsHue = new PartPhilipsHue("192.168.1.43:8000", "newdeveloper");
        List<DLamp> lampList = partPhilipsHue.getLamps();

        assertNotNull(lampList);
        assertTrue(lampList.size() > 0);


    }
}