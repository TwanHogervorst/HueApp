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

        assertEquals(0.286f, Utility.round(rgbColor.hue(),3));
        assertEquals(0.761f, Utility.round(rgbColor.saturation(),3));
        assertEquals(0.820f, Utility.round(rgbColor.brightness(),3));

        // hsb -> rgb values correct
        Color hsbColor = Color.fromHSV(0.286f, 0.761f,0.820f);

        assertEquals(95, hsbColor.red());
        assertEquals(209,hsbColor.green());
        assertEquals(50, hsbColor.blue());

        // hsb -> hsb values the same
        assertEquals(0.286f, Utility.round(hsbColor.hue(),3));
        assertEquals(0.761f, Utility.round(hsbColor.saturation(),3));
        assertEquals(0.820f, Utility.round(hsbColor.brightness(),3));

        // rgb == hsb
        assertEquals(rgbColor, hsbColor);

    }

    public void testColorBugCoverage() {

        // test pure (red) color
        // covers bug where a pure red, green or blue color results in black (r=0;g=0;b=0)
        Color redColor = Color.fromHSV(0, 1, 1);

        assertEquals(new Color(255, 0, 0), redColor);

    }

}