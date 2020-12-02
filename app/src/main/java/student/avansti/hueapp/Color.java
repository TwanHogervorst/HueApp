package student.avansti.hueapp;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Color {

    private int r;
    private int g;
    private int b;

    private float h;
    private float s;
    private float v;

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

    public float hue() {
        return this.h;
    }

    public float saturation() {
        return this.s;
    }

    public float brightness() {
        return this.v;
    }

    public android.graphics.Color asAndroidColor() {
        return android.graphics.Color.valueOf(
                Utility.map(this.red(), 0, 255,0, 1),
                Utility.map(this.green(), 0, 255, 0, 1),
                Utility.map(this.blue(), 0, 255, 0,1)
        );
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
        float r = this.r / 255.0f;
        float g = this.g / 255.0f;
        float b = this.b / 255.0f;

        // h, s, v = hue, saturation, value
        float cmax = Math.max(r, Math.max(g, b)); // maximum of r, g, b
        float cmin = Math.min(r, Math.min(g, b)); // minimum of r, g, b
        float diff = cmax - cmin; // diff of cmax and cmin.
        float h = -1, s = -1;

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
        float v = cmax * 100;

        this.h = Utility.map(h, 0, 360, 0, 1);
        this.s = Utility.map(s,0,100,0,1);
        this.v = Utility.map(v,0,100,0,1);
    }

    public static Color fromAndroidColor(@NotNull android.graphics.Color clr) {
        return Color.fromFloatRgb(clr.red(), clr.green(),clr.blue());
    }

    // Source: https://stackoverflow.com/questions/7896280/converting-from-hsv-hsb-in-java-to-rgb-without-using-java-awt-color-disallowe
    public static Color fromHSV(float hue, float sat, float val) {
        Color result = null;

        int h = (int)(hue * 6);
        float f = hue * 6 - h;
        float p = val * (1 - sat);
        float q = val * (1 - f * sat);
        float t = val * (1 - (1 - f) * sat);

        switch (h) {
            case 0: result = Color.fromFloatRgb(val, t, p); break;
            case 1: result = Color.fromFloatRgb(q, val, p); break;
            case 2: result = Color.fromFloatRgb(p, val, t); break;
            case 3: result = Color.fromFloatRgb(p, q, val); break;
            case 4: result = Color.fromFloatRgb(t, p, val); break;
            case 5: result = Color.fromFloatRgb(val, p, q); break;
            default: result = new Color(0, 0, 0);
        }

        return result;
    }

    private static Color fromFloatRgb(float r, float g, float b) {
        return new Color(
                (int)Utility.map(r, 0, 1,0,255),
                (int)Utility.map(g,0,1, 0, 255),
                (int)Utility.map(b, 0, 1, 0, 255)
        );
    }

}
