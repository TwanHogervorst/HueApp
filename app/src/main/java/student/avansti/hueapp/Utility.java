package student.avansti.hueapp;

import androidx.annotation.Nullable;

public class Utility {

    public static boolean stringIsNullOrEmpty(@Nullable String str) {
        return str == null || str.isEmpty();
    }

    public static boolean stringIsNullOrWhitespace(@Nullable String str) {
        return Utility.stringIsNullOrEmpty(str) || str.matches("^\\s*$");
    }

    public static double map(double value, double start1, double stop1, double start2, double stop2){
        return ((value - start1) / (stop1 - start1) * (stop2 - start2) + start2);
    }

    public static float map(float value, float start1, float stop1, float start2, float stop2){
        return ((value - start1) / (stop1 - start1) * (stop2 - start2) + start2);
    }

    public static float round(float value, int decimals) {
        float pow = (float)Math.pow(10, decimals);
        return Math.round(value * pow) / pow;
    }

    public static double round(double value, int decimals) {
        double pow = Math.pow(10, decimals);
        return Math.round(value * pow) / pow;
    }

}
