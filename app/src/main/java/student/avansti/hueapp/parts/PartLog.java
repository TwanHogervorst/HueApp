package student.avansti.hueapp.parts;

import android.util.Log;

public abstract class PartLog {

    private static PartLog instance;

    public abstract void d(String logTag, String message);

    public abstract void w(String logTag, String message, Throwable t);

    public abstract void e(String logTag, String message, Throwable t);

    public static void debug(String message) {
        PartLog.debug("DebugLog", message);
    }

    public static void debug(String logTag, String message) {
        PartLog.getInstance().d(logTag, message);
    }

    public static void warning(String logTag, String message) {
        PartLog.warning(logTag, message, null);
    }

    public static void warning(String logTag, Throwable t) {
        PartLog.warning(logTag, null, t);
    }

    public static void warning(String logTag, String message, Throwable t) {
        PartLog.getInstance().w(logTag, message, t);
    }

    public static void error(String logTag, String message) {
        PartLog.error(logTag, message, null);
    }

    public static void error(String logTag, Throwable t) {
        PartLog.error(logTag, null, t);
    }

    public static void error(String logTag, String message, Throwable t) {
        PartLog.getInstance().e(logTag, message, t);
    }

    private static PartLog getInstance() {
        if(PartLog.instance == null) {

            PartLog.setInstance(new PartSoutLog());

            try {
                Log.i(PartLog.class.getSimpleName(), "PartLog Initializing...");
                PartLog.setInstance(new PartAndroidLog());
            }
            catch (RuntimeException ex) {
                // jammer dan
            }

        }

        return PartLog.instance;
    }

    public static void setInstance(PartLog logInstance) {
        PartLog.instance = logInstance;
    }

}

class PartAndroidLog extends PartLog {

    @Override
    public void d(String logTag, String message) {
        Log.d(logTag, message);
    }

    @Override
    public void w(String logTag, String message, Throwable t) {
        Log.w(logTag, message, t);
    }

    @Override
    public void e(String logTag, String message, Throwable t) {
        Log.e(logTag, message, t);
    }
}

class PartSoutLog extends PartLog {

    @Override
    public void d(String logTag, String message) {
        System.out.print("D/");
        System.out.print(logTag);
        System.out.print(": ");
        System.out.println(message);
    }

    @Override
    public void w(String logTag, String message, Throwable t) {

        if(message == null && t != null) {
            message = t.getLocalizedMessage();
        }

        // set color to white
        System.out.print("\u001B[37m");

        System.out.print("W/");
        System.out.print(logTag);
        System.out.print(":");

        if(message != null) {
            System.out.print(" ");
            System.out.print(message);
        }
        if(t != null) {
            System.out.print(" ex=");
            t.printStackTrace();
        }

        // reset color
        System.out.println("\u001B[0m");

    }

    @Override
    public void e(String logTag, String message, Throwable t) {

        if(message == null && t != null) {
            message = t.getLocalizedMessage();
        }

        // set color to red
        System.out.print("\u001B[31m");

        System.out.print("E/");
        System.out.print(logTag);
        System.out.print(":");

        if(message != null) {
            System.out.print(" ");
            System.out.print(message);
        }
        if(t != null) {
            System.out.print(" ex=");
            t.printStackTrace();
        }

        // reset color
        System.out.println("\u001B[0m");

    }
}
