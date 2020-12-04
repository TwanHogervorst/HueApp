package student.avansti.hueapp.parts;

import junit.framework.TestCase;

public class PartUrlTest extends TestCase {

    public void testToString() {

        // To string must be equal to result
        PartUrl partUrl = new PartUrl("example.com/api");

        assertEquals(partUrl.result(), partUrl.toString());

    }

    public void testQueryParameters() {

        PartUrl partUrl = new PartUrl("example.com/api");

        // nothing changes if nothing done
        assertEquals("example.com/api", partUrl.result());

        // Add 1 parameter
        partUrl.addQueryParam("test", "valToOvrwr");

        assertTrue(partUrl.containsQueryParam("test"));
        assertEquals("example.com/api?test=valToOvrwr", partUrl.result());

        // overwriting value parameter 1
        partUrl.addQueryParam("test", "value");

        assertTrue(partUrl.containsQueryParam("test"));
        assertEquals("example.com/api?test=value", partUrl.result());

        // Add 2nd parameter (order doesn't matter)
        partUrl.addQueryParam("test2", "value2");

        assertTrue(partUrl.containsQueryParam("test"));
        assertTrue(partUrl.containsQueryParam("test2"));
        assertEquals("example.com/api?test2=value2&test=value", partUrl.result());

        // removing 1st parameter
        partUrl.removeQueryParam("test");
        assertFalse(partUrl.containsQueryParam("test"));
        assertTrue(partUrl.containsQueryParam("test2"));
        assertEquals("example.com/api?test2=value2", partUrl.result());

        // removing all parameters
        partUrl.removeQueryParam("test2");
        assertFalse(partUrl.containsQueryParam("test"));
        assertFalse(partUrl.containsQueryParam("test2"));
        assertEquals("example.com/api", partUrl.result());

        // We don't expect query parameters in url to be detected
        partUrl = new PartUrl("example.com/api?test=value");
        assertFalse(partUrl.containsQueryParam("test"));

    }

    public void testSubPaths() {

        PartUrl partUrl = new PartUrl("example.com/api");

        // nothing changes if nothing done
        assertEquals("example.com/api", partUrl.result());

        // We don't expect subdirs in base url to be detected
        assertFalse(partUrl.containsSubPath("api"));

        // add 1 sub path
        partUrl.addSubPath("test");

        assertTrue(partUrl.containsSubPath("test"));
        assertEquals("example.com/api/test", partUrl.result());

        // add 2nd sub path
        partUrl.addSubPath("test2");

        assertTrue(partUrl.containsSubPath("test"));
        assertTrue(partUrl.containsSubPath("test2"));
        assertEquals("example.com/api/test/test2", partUrl.result());

        // double entries
        partUrl.addSubPath("test2");

        assertTrue(partUrl.containsSubPath("test"));
        assertTrue(partUrl.containsSubPath("test2"));
        assertEquals("example.com/api/test/test2/test2", partUrl.result());

        // remove 2 entries
        partUrl.removeSubPath("test2");
        partUrl.removeSubPath("test");

        assertFalse(partUrl.containsSubPath("test"));
        assertTrue(partUrl.containsSubPath("test2"));
        assertEquals("example.com/api/test2", partUrl.result());

        // remove all entries
        partUrl.removeSubPath("test2");

        assertFalse(partUrl.containsSubPath("test"));
        assertFalse(partUrl.containsSubPath("test2"));
        assertEquals("example.com/api", partUrl.result());
    }

    public void testResult() {

        PartUrl partUrl = new PartUrl("example.com/api");

        // result returns unmodified url
        assertEquals("example.com/api", partUrl.result());

        // trailing / gets removed
        partUrl = new PartUrl("example.com/api/");

        assertEquals("example.com/api", partUrl.result());

        // adding sub paths and query params
        partUrl.addSubPath("test");
        partUrl.addQueryParam("test", "value");

        assertEquals("example.com/api/test?test=value", partUrl.result());

    }

}