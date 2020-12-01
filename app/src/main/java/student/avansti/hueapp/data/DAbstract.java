package student.avansti.hueapp.data;

import com.google.gson.Gson;

import java.io.Serializable;

public abstract class DAbstract implements Serializable {

    public String toJson() {
        return new Gson().toJson(this);
    }

}
