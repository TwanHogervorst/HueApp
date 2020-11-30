package student.avansti.hueapp;

import androidx.annotation.Nullable;

public class Utility {

    public static boolean stringIsNullOrEmpty(@Nullable String str) {
        return str == null || str.isEmpty();
    }

    public static boolean stringIsNullOrWhitespace(@Nullable String str) {
        return Utility.stringIsNullOrEmpty(str) || str.matches("^\\s*$");
    }

}
