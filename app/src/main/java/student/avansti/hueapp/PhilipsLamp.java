package student.avansti.hueapp;

import java.io.Serializable;

public class PhilipsLamp implements Serializable {

    private String name;
    private String state;
    private String color;
    private String lastinstall;
    private String type;
    private String modelID;

    public PhilipsLamp(String name, String state, String color, String lastinstall, String type, String modelID) {
        this.name = name;
        this.state = state;
        this.color = color;
        this.lastinstall = lastinstall;
        this.type = type;
        this.modelID = modelID;
    }

    public String getLampName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getColor() {
        return color;
    }

    public String getLastinstall() {
        return lastinstall;
    }

    public String getType() {
        return type;
    }

    public String getModelID() {
        return modelID;
    }

    public String GetCaption(){
        StringBuilder stringBuilder = new StringBuilder(50);
        stringBuilder.append(" Philips lamp name: ");
        stringBuilder.append(name);
        return stringBuilder.toString();
    }
}
