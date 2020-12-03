package student.avansti.hueapp;

import junit.framework.TestCase;

public class UtilityTest extends TestCase {

    public void testStringNullOrEmpty() {
        assertTrue(Utility.stringIsNullOrEmpty(null));
        assertTrue(Utility.stringIsNullOrEmpty(""));
        assertFalse(Utility.stringIsNullOrEmpty("unittest"));
    }

    public void testStringNullOrWhiteSpace() {
        assertTrue(Utility.stringIsNullOrWhitespace(null));
        assertTrue(Utility.stringIsNullOrWhitespace(""));
        assertTrue(Utility.stringIsNullOrWhitespace("          "));
        assertFalse(Utility.stringIsNullOrWhitespace("   unit   test   "));
        assertFalse(Utility.stringIsNullOrWhitespace("unittest"));
    }

    public void testMap() {

    }

    public void testRound() {

        assertEquals(0.1, Utility.round(0.07, 1));
        assertEquals(8.0, Utility.round(8.4, 0));
        assertEquals(20.9356, Utility.round(20.93555,4));

    }
}