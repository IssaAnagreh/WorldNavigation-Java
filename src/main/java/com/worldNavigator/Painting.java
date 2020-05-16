package com.worldNavigator;

import org.json.simple.JSONObject;

public class Painting extends Item {
    public final String LOCATION;
    public final String NAME = "Painting";

    public Painting(JSONObject painting) {
        this.LOCATION = (String) painting.get("location");
        if (painting.get("existed").equals("true")) {
            super.setCheckBehavior(new Unlocked_Checkable(painting, this.LOCATION));
        }
    }

    public String getLocation() {
        return this.LOCATION;
    }

    @Override
    public String getName() {
        return this.NAME;
    }

    public String getType() {
        return "painting";
    }

    public String getDetails() {
        return this.NAME + " in " + this.LOCATION;
    }

    @Override
    public String toString() {
        return "Painting, Location: " + this.LOCATION;
    }
}
