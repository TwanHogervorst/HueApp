package student.avansti.hueapp;

import androidx.annotation.ColorRes;

import junit.framework.TestCase;

import student.avansti.hueapp.parts.PartLog;

import static org.junit.Assert.assertNotEquals;

public class ColorTest extends TestCase {

    public void testColorEquals() {

        Color clr = new Color(255, 255, 255);
        Color clr2 = new Color(255, 255, 255);
        Color clr3 = new Color(0, 0, 0);

        assertEquals(clr, clr2);
        assertNotEquals(clr, clr3);

    }

    public void testColorHSB() {

        // rgb -> hsb values correct
        Color rgbColor = new Color(95, 209, 50);

        assertEquals(103, (int)Math.round(rgbColor.hue()));
        assertEquals(76.1, Utility.round(rgbColor.saturation(),1));
        assertEquals(82.0, Utility.round(rgbColor.brightness(),1));

        // hsb -> rgb values correct
        Color hsbColor = Color.fromHSV(103, 76.1,82.0);

        assertEquals(95, hsbColor.red());
        assertEquals(209,hsbColor.green());
        assertEquals(50, hsbColor.blue());

        // hsb -> hsb values the same
        assertEquals(103, (int)Math.round(rgbColor.hue()));
        assertEquals(76.1, Utility.round(hsbColor.saturation(),1));
        assertEquals(82.0, Utility.round(hsbColor.brightness(),1));

        // rgb == hsb
        assertEquals(rgbColor, hsbColor);

    }

    public void testColorRgbAsInt() {
        Color clr = new Color(170,170, 170);
        Color clr2 = Color.fromRgbInt(0xAAAAAA);

        assertEquals(0xAAAAAA, clr.rgbAsInt());
        assertEquals(clr, clr2);

    }

    public void testColorBugCoverage() {

        // test pure (red) color
        // covers bug where a pure red, green or blue color results in black (r=0;g=0;b=0)
        Color redColor = Color.fromHSV(0, 100, 100);

        assertEquals(new Color(255, 0, 0), redColor);

    }

}