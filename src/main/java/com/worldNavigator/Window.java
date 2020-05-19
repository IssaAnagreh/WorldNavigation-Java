package com.worldNavigator;

import org.json.simple.JSONObject;

public class Window extends Item {
    public final String NAME;
    private final String LOCATION;

    public Window(JSONObject door) {
        this.NAME = "Window";
        this.LOCATION = (String) door.get("location");

        super.setCheckBehavior(new Uncheckable());
    }

    @Override
    public String getLocation() {
        return this.LOCATION;
    }

    @Override
    public String getName() {
        return this.NAME + " Looks to the blue sky";
    }

    @Override
    public String getType() {
        return "Window";
    }

    @Override
    public String toString() {
        return "Window: " + this.NAME + ", in " + this.LOCATION;
    }

    @Override
    public int compareTo(String location) {
        return this.getLocation().compareTo(location);
    }
}
