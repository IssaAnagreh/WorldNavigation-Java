package com.worldNavigator;

import org.json.simple.JSONObject;

public class Mirror extends Item {
    private final String LOCATION;
    private final String NAME = "Mirror";

    public Mirror(JSONObject mirror) {
        this.LOCATION = (String) mirror.get("location");
        if (mirror.get("existed").equals("true")) {
            super.setCheckBehavior(new Unlocked_Checkable(mirror, this.LOCATION));
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
        return "mirror";
    }

    @Override
    public String getDetails() {
        return "You see a silhouette of you because of a " + this.NAME + " in " + this.LOCATION;
    }

    @Override
    public String toString() {
        return "Mirror, Location: " + this.LOCATION;
    }
}
