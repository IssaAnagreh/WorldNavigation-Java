package com.worldNavigator;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Door extends Openable implements Item {
    public final String NAME;
    private final Boolean IS_GOLDEN;
    private final String NEXT_ROOM;
    private final String LOCATION;

    public Door(JSONObject door) {
        this.NAME = (String) door.get("name");
        this.IS_GOLDEN = door.get("golden").equals("true");
        if (door.get("key") != null) {
            setKey(new Key((String) door.get("key")));
        }
        initIs_locked(door.get("is_locked").equals("true"));
        this.NEXT_ROOM = (String) door.get("to");
        this.LOCATION = (String) door.get("location");
    }

    public String getNextRoom() {
        if (getIs_locked()) {
            return "locked";
        } else {
            if (this.getGolden()) {
                return "golden";
            };
            return this.NEXT_ROOM;
        }
    }

    public String getDetails() {
        return this.NAME + " in " + this.LOCATION;
    }

    private Boolean getGolden() {
        return this.IS_GOLDEN;
    }

    public String getLocation() {
        return this.LOCATION;
    }

    public String getName() {
        return this.NAME;
    }

    public String getType() {
        return "door";
    }

    @Override
    public String toString() {
        return "door";
    }
}
