package student.avansti.hueapp.data;

import com.google.gson.Gson;

public abstract class DAbstract {

    public String toJson() {
        return new Gson().toJson(this);
    }

}
