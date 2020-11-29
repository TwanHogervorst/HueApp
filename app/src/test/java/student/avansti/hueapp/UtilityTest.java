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
}