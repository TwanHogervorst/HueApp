package student.avansti.hueapp.data;

import com.google.gson.Gson;

import java.io.Serializable;

import student.avansti.hueapp.parts.PartGson;

public abstract class DAbstract implements Serializable {

    public String toJson() {
        return PartGson.getGsonInstance().toJson(this);
    }

}
