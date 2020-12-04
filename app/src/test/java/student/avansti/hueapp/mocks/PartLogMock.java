package student.avansti.hueapp.mocks;

import student.avansti.hueapp.parts.PartLog;

public class PartLogMock extends PartLog {

    @Override
    public void d(String logTag, String message) {

    }

    @Override
    public void w(String logTag, String message, Throwable t) {

    }

    @Override
    public void e(String logTag, String message, Throwable t) {

    }
}
