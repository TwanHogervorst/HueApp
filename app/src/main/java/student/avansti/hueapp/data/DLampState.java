package student.avansti.hueapp.data;

import student.avansti.hueapp.Color;
import student.avansti.hueapp.Utility;

public class DLampState extends DAbstract {

    public int[] xy = new int[2];
    public int ct;
    public String alert;
    public int sat;
    public String effect;
    public int bri;
    public int hue;
    public String colormode;
    public boolean reachable;
    public boolean on;

    public Color getColor() {
        return Color.fromHSV(
                Utility.map(this.hue, 0, 65535, 0, 360),
                Utility.map(this.sat,0, 254,0,100),
                Utility.map(this.bri,1,254,0, 100)
        );
    }

}
