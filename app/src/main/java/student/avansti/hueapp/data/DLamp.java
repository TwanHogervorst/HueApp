package student.avansti.hueapp.data;

import java.util.HashMap;
import java.util.Map;

import student.avansti.hueapp.annotation.GsonExclude;

public class DLamp extends DAbstract {

    @GsonExclude
    public String id;
    public String modelid;
    public String name;
    public String swversion;
    public DLampState state = new DLampState();
    public String type;
    public Map<String, String> pointsymbol = new HashMap<>();
    public String uniqueid;


}
