package student.avansti.hueapp;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Color {

    private int r;
    private int g;
    private int b;

    private double h;
    private double s;
    private double v;

    public Color(int r, int g, int b) {
        this.r = r % 256;
        this.g = g % 256;
        this.b = b % 256;

        this.calculateHSV();
    }

    public int red() {
        return this.r;
    }

    public int green() {
        return this.g;
    }

    public int blue() {
        return this.b;
    }

    public double hue() {
        return this.h;
    }

    public double saturation() {
        return this.s;
    }

    public double brightness() {
        return this.v;
    }

    public android.graphics.Color asAndroidColor() {
        return android.graphics.Color.valueOf(
                Utility.map(this.red(), 0, 255,0, 1),
                Utility.map(this.green(), 0, 255, 0, 1),
                Utility.map(this.blue(), 0, 255, 0,1)
        );
    }

    public int rgbAsInt() {
        return (this.r << 16) | (this.g << 8) | this.b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return r == color.r &&
                g == color.g &&
                b == color.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }

    @Override
    public String toString() {
        return "Color{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }

    // Source: https://www.geeksforgeeks.org/program-change-rgb-color-model-hsv-color-model/
    private void calculateHSV() {
        // R, G, B values are divided by 255
        // to change the range from 0..255 to 0..1
        double r = this.r / 255.0;
        double g = this.g / 255.0;
        double b = this.b / 255.0;

        // h, s, v = hue, saturation, value
        double cmax = Math.max(r, Math.max(g, b)); // maximum of r, g, b
        double cmin = Math.min(r, Math.min(g, b)); // minimum of r, g, b
        double diff = cmax - cmin; // diff of cmax and cmin.
        double h = -1, s = -1;

        // if cmax and cmax are equal then h = 0
        if (cmax == cmin)
            h = 0;

            // if cmax equal r then compute h
        else if (cmax == r)
            h = (60 * ((g - b) / diff) + 360) % 360;

            // if cmax equal g then compute h
        else if (cmax == g)
            h = (60 * ((b - r) / diff) + 120) % 360;

            // if cmax equal b then compute h
        else if (cmax == b)
            h = (60 * ((r - g) / diff) + 240) % 360;

        // if cmax equal zero
        if (cmax == 0)
            s = 0;
        else
            s = (diff / cmax) * 100;

        // compute v
        double v = cmax * 100;

        this.h = h;
        this.s = s;
        this.v = v;
    }

    public static Color fromAndroidColor(@NotNull android.graphics.Color clr) {
        return Color.fromFloatRgb(clr.red(), clr.green(),clr.blue());
    }

    // Source: https://www.rapidtables.com/convert/color/hsv-to-rgb.html
    public static Color fromHSV(double hue, double sat, double val) {
        Color result = null;

        hue %= 360;
        sat = Math.min(sat, 100) / 100.0;
        val = Math.min(val, 100) / 100.0;

        int h = (int)(hue / 60);
        double c = val * sat;
        double x = c * (1 - Math.abs((hue / 60.0) % 2 - 1));
        double m = val - c;

        c += m;
        x += m;

        switch (h) {
            case 0: result = Color.fromDoubleRgb(c, x, m); break;
            case 1: result = Color.fromDoubleRgb(x, c, m); break;
            case 2: result = Color.fromDoubleRgb(m, c, x); break;
            case 3: result = Color.fromDoubleRgb(m, x, c); break;
            case 4: result = Color.fromDoubleRgb(x, m, c); break;
            case 5: result = Color.fromDoubleRgb(c, m, x); break;
            default: result = new Color(0, 0, 0);
        }

        return result;
    }

    public static Color fromRgbInt(int rgb) {
        return new Color(
                (rgb >> 16) & 0xFF,
                (rgb >> 8) & 0xFF,
                rgb & 0xFF
        );
    }

    private static Color fromFloatRgb(float r, float g, float b) {
        return new Color(
                Math.round(r * 255f),
                Math.round(g * 255f),
                Math.round(b * 255f)
        );
    }

    private static Color fromDoubleRgb(double r, double g, double b) {
        return new Color(
                (int)Math.round(r * 255.0),
                (int)Math.round(g * 255.0),
                (int)Math.round(b * 255.0)
        );
    }

}
