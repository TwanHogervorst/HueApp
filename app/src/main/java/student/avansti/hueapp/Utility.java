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

}
